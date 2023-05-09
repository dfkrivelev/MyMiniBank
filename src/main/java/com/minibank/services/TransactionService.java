package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.constants.StatusTransaction;
import com.minibank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById (Long id) {
        return transactionRepository.findById(id).get();
    }

    @Transactional
    public Transaction create(Transaction transaction, Long fromAccountId, Long toAccountNumber) {
        //TODO Сделать так, что мы вводим номер счетом, но связываение будет по id

        transaction.setAccountFrom(accountService.findById(fromAccountId));
        transaction.setAccountTo(accountService.findByAccountNumber(toAccountNumber));
        transaction.setDateTime(OffsetDateTime.now());
        transaction.setStatus(StatusTransaction.COMPLETED);

        return transactionRepository.save(transaction);
    }

    @Transactional
    private Transaction reverseTransaction(Transaction transaction, int fromAccountId, int toAccountNumber) {
        Transaction reverseTransaction = transaction;



        return transactionRepository.save(reverseTransaction);
    }

    @Transactional
    public void changeStatus(Transaction transaction, StatusTransaction status) {
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }
}