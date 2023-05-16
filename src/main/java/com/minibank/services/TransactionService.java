package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.constants.StatusTransaction;
import com.minibank.models.constants.TypeTransfer;
import com.minibank.repositories.AccountRepository;
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
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById (Long id) {
        return transactionRepository.findById(id).get();
    }

    @Transactional
    public void transfer(Transaction transaction, Long fromAccountNumber, Long toAccountNumber) {
        Transaction newTransaction = transaction;
        Account fromAccount = accountService.findByAccountNumber(fromAccountNumber);
        Account toAccount = accountService.findByAccountNumber(toAccountNumber);

        newTransaction.setAccountFrom(fromAccount);
        newTransaction.setAccountTo(toAccount);
        newTransaction.setDateTime(OffsetDateTime.now());
        newTransaction.setAmount(newTransaction.getAmount() * -1);
        newTransaction.setStatus(StatusTransaction.COMPLETED);
        newTransaction.setTypeTransfer(TypeTransfer.EXPENSE);

        if(fromAccount.getBalance() != 0 && fromAccount.getBalance() >= newTransaction.getAmount()){
            fromAccount.setBalance(fromAccount.getBalance() + newTransaction.getAmount());
        }
        accountService.addTransaction(fromAccount, newTransaction);
        accountRepository.save(fromAccount);

        reverseTransaction(newTransaction, toAccount);
//        transactionRepository.save(newTransaction);
    }

    @Transactional
    public void reverseTransaction(Transaction transaction, Account toAccount) {
        Transaction reverseTransaction = new Transaction(transaction.getAccountFrom(), toAccount,
                transaction.getAmount(), transaction.getDescription());

        reverseTransaction.setDateTime(OffsetDateTime.now());
        reverseTransaction.setAmount(reverseTransaction.getAmount() * -1);
        reverseTransaction.setStatus(StatusTransaction.COMPLETED);
        reverseTransaction.setTypeTransfer(TypeTransfer.INCOME);

        toAccount.setBalance(toAccount.getBalance() + reverseTransaction.getAmount());

        accountService.addTransaction(toAccount, reverseTransaction);

        accountRepository.save(toAccount);
    }

    @Transactional
    public void changeStatus(Transaction transaction, StatusTransaction status) {
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }
}