package com.minibank.models;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "account_to")
    private Account accountTo;
    @Column(name = "account_from")
    private Account accountFrom;
    @Column(name = "date_creation")
    private OffsetDateTime dateTime;
    @Column(name = "amount")
    private Double amount;

    public Transaction() {
    }

    public Transaction(Account accountTo, Account accountFrom, OffsetDateTime dateTime, Double amount) {
        this.accountTo = accountTo;
        this.accountFrom = accountFrom;
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountTo=" + accountTo +
                ", accountFrom=" + accountFrom +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                '}';
    }
}
