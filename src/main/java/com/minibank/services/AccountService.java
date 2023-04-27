package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.constants.Status;
import com.minibank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(int accountId) {
        return accountRepository.findById(accountId).get();
    }

    @Transactional
    public Account create(Account account) {
       return accountRepository.save(account);
    }

    @Transactional
    public void changeStatus (Account account, Status status) {
        account.setStatus(status);
        accountRepository.save(account);
    }

    public void addTransaction(Account account, Transaction transaction){
        List<Transaction> transactions = account.getTransactions();
        transactions.add(transaction);
        account.setTransactions(transactions);
    }
}
