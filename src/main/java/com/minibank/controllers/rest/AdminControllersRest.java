package com.minibank.controllers.rest;

import com.minibank.models.User;
import com.minibank.services.AccountService;
import com.minibank.services.TransactionService;
import com.minibank.services.UserService;
import com.minibank.vo.AccountVO;
import com.minibank.vo.InlineObject;
import com.minibank.vo.TransactionVO;
import com.minibank.vo.UserVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
    public ResponseEntity<AccountVO> createAccount(AccountVO body) {
        return null;
    }

    @Override
    public ResponseEntity<AccountVO> getAccountById(Long accountId) {
        return null;
    }

    @Override
    public ResponseEntity<List<AccountVO>> getAllAccounts(Integer page, Integer perPage) {
        return null;
    }

    @Override
    public ResponseEntity<List<TransactionVO>> getAllTransactions(Integer page, Integer perPage) {
        return null;
    }

    @Override
    public ResponseEntity<List<UserVO>> getAllUsers(Integer page, Integer perPage) {
        return null;
    }

    @Override
    public ResponseEntity<TransactionVO> getTransactionById(Long transactionId) {
        return null;
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
        return null;
    }


}
