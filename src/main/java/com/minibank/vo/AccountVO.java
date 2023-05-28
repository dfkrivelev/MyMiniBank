package com.minibank.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.User;
import com.minibank.models.constants.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountVO {

    private Long id;
    @Schema(example = "8452325", description = "")
    private Long accountNumber;
    @Schema(example = "2", description = "")
    @JsonBackReference
    private UserVO userVO;
    @Schema(example = "1523.00", description = "")
    private Double balance;
    @Schema(example = "2023-05-27T20:13:27.989Z", description = "")
    private OffsetDateTime dateTime;
    @Schema(example = "Active", description = "")
    private Status status;

//    @Schema(description = "List transactions", implementation = TransactionVO.class)
//    @JsonManagedReference
//    private List<TransactionVO> transactionsFrom = new ArrayList<>();
//
//    @Schema(description = "List transactions", implementation = TransactionVO.class)
//    @JsonManagedReference
//    private List<TransactionVO> transactionsTo = new ArrayList<>();

    public AccountVO(Long id, Long accountNumber, User user, Double balance, OffsetDateTime dateTime, Status status) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.userVO = UserVO.valueOf(user);
        this.balance = balance;
        this.dateTime = dateTime;
        this.status = status;
    }

    public AccountVO() {
    }

    public static AccountVO valueOf(Account account){
        AccountVO accountVO = new AccountVO(account.getId(), account.getAccountNumber(), account.getUser(), account.getBalance(),
                account.getDateTime(), account.getStatus());


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

    public UserVO getUser() {
        return userVO;
    }

    public void setUser(UserVO user) {
        this.userVO = user;
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

//    public List<TransactionVO> getTransactionsFrom() {
//        return transactionsFrom;
//    }
//
//    public void setTransactionsFrom(List<TransactionVO> transactionsFrom) {
//        this.transactionsFrom = transactionsFrom;
//    }
//
//    public List<TransactionVO> getTransactionsTo() {
//        return transactionsTo;
//    }
//
//    public void setTransactionsTo(List<TransactionVO> transactionsTo) {
//        this.transactionsTo = transactionsTo;
//    }
}