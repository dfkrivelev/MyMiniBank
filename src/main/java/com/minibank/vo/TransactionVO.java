package com.minibank.vo;

import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.constants.StatusTransaction;
import com.minibank.models.constants.TypeTransfer;

import java.time.OffsetDateTime;

public class TransactionVO {

    private Long id;
    private Account accountFrom;
    private Account accountTo;
    private OffsetDateTime dateTime;
    private Double amount;
    private StatusTransaction status;
    private TypeTransfer typeTransfer;
    private String description;

    public TransactionVO(Long id, Account accountFrom, Account accountTo, OffsetDateTime dateTime, Double amount, StatusTransaction status, TypeTransfer typeTransfer, String description) {
        this.id = id;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.dateTime = dateTime;
        this.amount = amount;
        this.status = status;
        this.typeTransfer = typeTransfer;
        this.description = description;
    }

    public TransactionVO valueOf(Transaction transaction){

        return new TransactionVO(transaction.getId(), transaction.getAccountFrom(),
                transaction.getAccountTo(), transaction.getDateTime(), transaction.getAmount(),
                transaction.getStatus(), transaction.getTypeTransfer(), transaction.getDescription());
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
}