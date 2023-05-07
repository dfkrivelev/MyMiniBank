package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.constants.Status;
import com.minibank.repositories.AccountRepository;
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

    @Autowired
    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(int accountId) {
        return accountRepository.findById(accountId).get();
    }

    public Account findByAccountNumber(int accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).get();
    }

    @Transactional
    public Account create(Account account, int id) {
        account.setUser(userService.findById(id));
        account.setBalance(0.0);
        account.setDateTime(OffsetDateTime.now());
        account.setStatus(Status.ACTIVE);
        userService.addUserAccounts(userService.findById(id), account);
       return accountRepository.save(account);
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
}
