package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.comparator.ComparatorDate;
import com.minibank.models.constants.Status;
import com.minibank.models.constants.TypeTransfer;
import com.minibank.repositories.AccountRepository;
import com.minibank.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserService userService, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long accountId) {
        return accountRepository.findById(accountId).get();
    }

    public Account findByAccountNumber(Long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).get();
    }

    @Transactional
    public void create(Account account, Long id) {
        Account newAccount = new Account(account.getAccountNumber(), userRepository.getReferenceById(id));

        userService.addUserAccounts(userRepository.getReferenceById(id), newAccount);
        accountRepository.save(newAccount);
        logger.info("create new Account, account={}",account.getId());
    }

    @Transactional
    public void changeStatus(Account account, Status status) {
        account.setStatus(status);
        accountRepository.save(account);
    }


    public void addExpenseTransaction(Account account, Transaction transaction) {
        List<Transaction> transactions;
        transactions = account.getTransactionsFrom();
        transactions.add(transaction);
        account.setTransactionsFrom(transactions);
    }

    public void addIncomeTransaction(Account account, Transaction transaction) {
        List<Transaction> transactions;
        transactions = account.getTransactionsTo();
        transactions.add(transaction);
        account.setTransactionsTo(transactions);
    }

    @Transactional
    public Account changeBalance(Account account, double amount) {
        account.setBalance(amount);
        return accountRepository.save(account);
    }

    public List<Transaction> allTransactions(Account account) {
        List<Transaction> allTransactions = new ArrayList<>();

        allTransactions.addAll(expenseTransactions(account));
        allTransactions.addAll(incomeTransactions(account));

        allTransactions.sort(new ComparatorDate());

        return allTransactions;
    }

    public List<Transaction> expenseTransactions(Account account) {
        List<Transaction> expenseTransactions = new ArrayList<>();

        for (Transaction transaction : account.getTransactionsFrom()) {
            if (transaction.getTypeTransfer().equals(TypeTransfer.EXPENSE)) {
                expenseTransactions.add(transaction);
            }
        }
        return expenseTransactions;
    }

    public List<Transaction> incomeTransactions(Account account) {
        List<Transaction> incomeTransactions = new ArrayList<>();

        for (Transaction transaction : account.getTransactionsTo()) {
            if (transaction.getTypeTransfer().equals(TypeTransfer.INCOME)) {
                incomeTransactions.add(transaction);
            }
        }
        return incomeTransactions;
    }
}
