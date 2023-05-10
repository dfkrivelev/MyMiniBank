package com.minibank.models;

import com.minibank.models.constants.StatusTransaction;
import com.minibank.models.constants.TypeTransfer;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Account.class)
    @JoinColumn(name = "account_from_id", nullable = false)
    private Account accountFrom;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Account.class)
    @JoinColumn(name = "account_to_id", nullable = false)
    private Account accountTo;
    @Column(name = "date_creation")
    private OffsetDateTime dateTime;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "status_transaction")
    @Enumerated(EnumType.STRING)
    private StatusTransaction status;
    @Column(name = "type_transfer")
    @Enumerated(EnumType.STRING)
    private TypeTransfer typeTransfer;
    @Column(name = "description")
    private String description;

    public Transaction() {
    }

    public Transaction(Account accountFrom, Account accountTo, Double amount, String description) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
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

    public TypeTransfer getTypeTransfer() {
        return typeTransfer;
    }

    public void setTypeTransfer(TypeTransfer typeTransfer) {
        this.typeTransfer = typeTransfer;
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
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                ", status=" + status +
                ", typeTransfer=" + typeTransfer +
                ", description='" + description + '\'' +
                '}';
    }
}