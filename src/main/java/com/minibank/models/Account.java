package com.minibank.models;

import com.minibank.models.constants.Status;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "account_number")
    private int accountNumber;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "balance")
    private Double balance;
    @Column(name = "data_creation")
    private OffsetDateTime dateTime;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "accountFrom", cascade = CascadeType.ALL)
    private List<Transaction> transactionsFrom = new ArrayList<>();

    @OneToMany(mappedBy = "accountTo", cascade = CascadeType.ALL)
    private List<Transaction> transactionsTo = new ArrayList<>();

    public Account() {
    }

    public Account(int accountNumber, User user) {
        this.accountNumber = accountNumber;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
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

    public void setTransactionsFrom(List<Transaction> transactions) {
        this.transactionsFrom = transactions;
    }

    public List<Transaction> getTransactionsTo() {
        return transactionsTo;
    }

    public void setTransactionsTo(List<Transaction> transactionsTo) {
        this.transactionsTo = transactionsTo;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", user=" + user +
                ", balance=" + balance +
                ", dateTime=" + dateTime +
                ", status=" + status +
                '}';
    }
}