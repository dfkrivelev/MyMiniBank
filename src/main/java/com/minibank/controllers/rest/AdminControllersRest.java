package com.minibank.controllers.rest;


import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.User;
import com.minibank.models.constants.UserRole;
import com.minibank.services.AccountService;
import com.minibank.services.TransactionService;
import com.minibank.services.UserService;
import com.minibank.vo.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Admin")
public class AdminControllersRest implements AdminApi{

    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Autowired
    public AdminControllersRest(UserService userService, AccountService accountService, TransactionService transactionService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public ResponseEntity<AccountVO> createAccount(InlineObject1 inlineObject1) {
        Account account = accountService.create(inlineObject1.getAccountNumber(), inlineObject1.getUserId());
        AccountVO accountVO = AccountVO.valueOf(account);
        return ResponseEntity.ok(accountVO);
    }

    @Override
    @ResponseBody
    public ResponseEntity<AccountVO> getAccountById(Long accountId) {
        Account account = accountService.findById(accountId);
        AccountVO result = AccountVO.valueOf(account);
        return ResponseEntity.ok(result);
    }

    @Override
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AccountVO>> getAllAccounts(Integer page, Integer perPage) {
        List<Account> allAccounts = accountService.findAll();
        List<AccountVO> allAccountsVO = allAccounts.stream().map(AccountVO::valueOf).toList();
        return ResponseEntity.ok(allAccountsVO);
    }

    @Override
    public ResponseEntity<List<TransactionVO>> getAllTransactions(Integer page, Integer perPage) {
        List<Transaction> allTransaction = transactionService.findAll();
        List<TransactionVO> allTransactionVO = allTransaction.stream().map(TransactionVO::valueOf).toList();
        return ResponseEntity.ok(allTransactionVO);
    }

    @Override
    public ResponseEntity<List<UserVO>> getAllUsers(Integer page, Integer perPage) {
        List<User> allUsers = userService.findAll();
        List<UserVO> allUsersVO = allUsers.stream().map(UserVO::valueOf).toList();
        return ResponseEntity.ok(allUsersVO);
    }

    @Override
    @ResponseBody
    public ResponseEntity<TransactionVO> getTransactionById(Long transactionId) {
        Transaction transaction = transactionService.findById(transactionId);
        TransactionVO result = TransactionVO.valueOf(transaction);
        return ResponseEntity.ok(result);
    }

    @Override
    @ResponseBody
    public ResponseEntity<UserVO> getUserById(Long userId) {
        User user = userService.findById(userId);
        UserVO result = UserVO.valueOf(user);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<AccountVO> updateStatusAcc(Long accountId, InlineObject inlineObject) {
        Account account = accountService.findById(accountId);
        accountService.changeStatus(account, inlineObject.getStatus());
        AccountVO accountVO = AccountVO.valueOf(account);
        return ResponseEntity.ok(accountVO);
    }


}
