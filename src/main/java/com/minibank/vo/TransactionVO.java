package com.minibank.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.constants.StatusTransaction;
import com.minibank.models.constants.TypeTransfer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

public class TransactionVO {

    private Long id;
    @Schema(example = "1", description = "")
    @JsonBackReference
    private AccountVO accountFrom;
    @Schema(example = "2", description = "")
    @JsonBackReference
    private AccountVO accountTo;
    @Schema(example = "2023-05-27T20:13:27.989Z", description = "")
    private OffsetDateTime dateTime;
    @Schema(example = "200.00", description = "")
    private Double amount;
    @Schema(example = "COMPLETED", description = "")
    private StatusTransaction status;
    @Schema(example = "INCOME", description = "")
    private TypeTransfer typeTransfer;
    @Schema(example = "Transfer", description = "")
    private String description;

    public TransactionVO(Long id, Account accountFrom, Account accountTo, OffsetDateTime dateTime, Double amount, StatusTransaction status, TypeTransfer typeTransfer, String description) {
        this.id = id;
        this.accountFrom = AccountVO.valueOf(accountFrom);
        this.accountTo = AccountVO.valueOf(accountTo);
        this.dateTime = dateTime;
        this.amount = amount;
        this.status = status;
        this.typeTransfer = typeTransfer;
        this.description = description;
    }

    public TransactionVO() {
    }

    public static TransactionVO valueOf(Transaction transaction){

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

    public AccountVO getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(AccountVO accountFrom) {
        this.accountFrom = accountFrom;
    }

    public AccountVO getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(AccountVO accountTo) {
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