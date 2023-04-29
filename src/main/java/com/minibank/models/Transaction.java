package com.minibank.models;

import com.minibank.models.constants.StatusTransaction;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Account.class)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @Column(name = "account_to")
    private Integer accountTo;
    @Column(name = "date_creation")
    private OffsetDateTime dateTime;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "status_transaction")
    @Enumerated(EnumType.STRING)
    private StatusTransaction status;
    @Column(name = "description")
    private String description;

    public Transaction() {
    }

    public Transaction(User user ,Integer accountTo, Double amount, String description) {
        this.user = user;
        this.accountTo = accountTo;
        this.dateTime = OffsetDateTime.now();
        this.amount = amount;
        this.status = StatusTransaction.COMPLETED;
        this.description=description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Integer accountTo) {
        this.accountTo = accountTo;
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

    public StatusTransaction getStatus() {
        return status;
    }

    public void setStatus(StatusTransaction status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", user=" + user +
                ", account=" + account +
                ", accountTo=" + accountTo +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
