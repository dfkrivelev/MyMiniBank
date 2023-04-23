package com.minibank.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "account_number")
    private int accountNumber;
    @Column(name = "balance")
    private Double balance;
    @Column(name = "data_creation")
    private LocalDate dateOfCreationAccount;

    public Account() {
    }

    public Account(int accountNumber, Double balance, LocalDate dateOfCreationAccount) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.dateOfCreationAccount = dateOfCreationAccount;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getDateOfCreationAccount() {
        return dateOfCreationAccount;
    }

    public void setDateOfCreationAccount(LocalDate dateOfCreationAccount) {
        this.dateOfCreationAccount = dateOfCreationAccount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", dateOfCreationAccount=" + dateOfCreationAccount +
                '}';
    }
}
