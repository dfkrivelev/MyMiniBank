package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.constants.StatusTransaction;
import com.minibank.models.constants.TypeTransfer;
import com.minibank.repositories.AccountRepository;
import com.minibank.repositories.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TransactionService {

    private final static Logger logger = LoggerFactory.getLogger(TransactionService.class);

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

    public Transaction findById(Long id) {
        return transactionRepository.findById(id).get();
    }

    @Transactional
    public void transfer(Long fromAccountNumber, Long toAccountNumber, Double amount, String description) {
        Account fromAccount = accountService.findByAccountNumber(fromAccountNumber);
        Account toAccount = accountService.findByAccountNumber(toAccountNumber);
        Transaction newTransaction = new Transaction(fromAccount, toAccount, amount, description);

        if (fromAccount.equals(toAccount)) {
            fromAccount.setBalance(fromAccount.getBalance() + newTransaction.getAmount());
            newTransaction.setStatus(StatusTransaction.COMPLETED);
            newTransaction.setDateTime(OffsetDateTime.now());
            newTransaction.setTypeTransfer(TypeTransfer.INCOME);
            newTransaction.setDescription("Replenishment of the balance");
            accountService.addIncomeTransaction(fromAccount, newTransaction);
            accountRepository.save(fromAccount);
            return;
        }

        newTransaction.setAccountFrom(fromAccount);
        newTransaction.setAccountTo(toAccount);
        newTransaction.setDateTime(OffsetDateTime.now());
        newTransaction.setAmount(newTransaction.getAmount() * -1);
        newTransaction.setStatus(StatusTransaction.COMPLETED);
        newTransaction.setTypeTransfer(TypeTransfer.EXPENSE);

        if (fromAccount.getBalance() != 0 && fromAccount.getBalance() >= newTransaction.getAmount()) {
            fromAccount.setBalance(fromAccount.getBalance() + newTransaction.getAmount());
        }
        accountService.addExpenseTransaction(fromAccount, newTransaction);
        accountRepository.save(fromAccount);
        logger.info("create transaction, transactionId={}", newTransaction.getId());

        reverseTransaction(newTransaction, toAccount);
    }

    @Transactional
    private void reverseTransaction(Transaction transaction, Account toAccount) {
        Transaction reverseTransaction = new Transaction(transaction.getAccountFrom(), toAccount,
                transaction.getAmount(), transaction.getDescription());

        reverseTransaction.setDateTime(OffsetDateTime.now());
        reverseTransaction.setAmount(reverseTransaction.getAmount() * -1);
        reverseTransaction.setStatus(StatusTransaction.COMPLETED);
        reverseTransaction.setTypeTransfer(TypeTransfer.INCOME);

        toAccount.setBalance(toAccount.getBalance() + reverseTransaction.getAmount());

        accountService.addIncomeTransaction(toAccount, reverseTransaction);

        accountRepository.save(toAccount);
        logger.info("create reverse transaction, transactionId={}, for transaction transactionId={}",
                reverseTransaction.getId(), transaction.getId());
    }

    @Transactional
    public void changeStatus(Transaction transaction, StatusTransaction status) {
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }
}