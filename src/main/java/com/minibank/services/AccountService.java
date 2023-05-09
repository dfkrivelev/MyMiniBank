package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.constants.Status;
import com.minibank.repositories.AccountRepository;
import com.minibank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AccountService {

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
        Account newAccount = new Account(account.getAccountNumber(), userRepository.getReferenceById(id),
               account.getBalance());

        newAccount.setBalance(0.0);
        newAccount.setDateTime(OffsetDateTime.now());
        newAccount.setStatus(Status.ACTIVE);
        userService.addUserAccounts(userRepository.getReferenceById(id), newAccount);
        accountRepository.save(newAccount);
    }

    @Transactional
    public void changeStatus (Account account, Status status) {
        account.setStatus(status);
        accountRepository.save(account);
    }

    @Transactional
    public void addTransaction(Transaction transaction, Account account){
//        List<Transaction> transactions = account.getTransactions();
//        transactions.add(transaction);
//        account.setTransactions(transactions);
    }

    @Transactional
    public Account changeBalance(Account account ,double amount) {
        account.setBalance(amount);
        return accountRepository.save(account);
    }
}
