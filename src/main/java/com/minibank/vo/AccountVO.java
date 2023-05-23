package com.minibank.vo;

import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.User;
import com.minibank.models.constants.Status;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountVO {

    private Long id;
    private Long accountNumber;
    private User user;
    private Double balance;
    private OffsetDateTime dateTime;
    private Status status;
    private List<Transaction> transactionsFrom = new ArrayList<>();
    private List<Transaction> transactionsTo = new ArrayList<>();

    public AccountVO(Long id, Long accountNumber, User user, Double balance, OffsetDateTime dateTime, Status status) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.user = user;
        this.balance = balance;
        this.dateTime = dateTime;
        this.status = status;
    }

    public AccountVO valueOf(Account account){
        AccountVO accountVO = new AccountVO(account.getId(), account.getAccountNumber(), account.getUser(), account.getBalance(),
                account.getDateTime(), account.getStatus());
        accountVO.setTransactionsFrom(account.getTransactionsFrom());
        accountVO.setTransactionsTo(account.getTransactionsTo());

        return accountVO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Transaction> getTransactionsFrom() {
        return transactionsFrom;
    }

    public void setTransactionsFrom(List<Transaction> transactionsFrom) {
        this.transactionsFrom = transactionsFrom;
    }

    public List<Transaction> getTransactionsTo() {
        return transactionsTo;
    }

    public void setTransactionsTo(List<Transaction> transactionsTo) {
        this.transactionsTo = transactionsTo;
    }
}