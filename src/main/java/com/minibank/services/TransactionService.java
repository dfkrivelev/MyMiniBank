package com.minibank.services;

import com.minibank.models.Transaction;
import com.minibank.models.constants.StatusTransaction;
import com.minibank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById (int id) {
        return transactionRepository.findById(id).get();
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Transactional
    private Transaction reverseTransaction(Transaction transaction) {
        //TODO Написать логику создания обратной транзакции для счета на который переводят деньги
        Transaction newTransaction = transaction;
        return transactionRepository.save(newTransaction);
    }

    @Transactional
    public void changeStatus(Transaction transaction, StatusTransaction status) {
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }
}
