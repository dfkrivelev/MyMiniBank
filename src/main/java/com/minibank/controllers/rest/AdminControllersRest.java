package com.minibank.controllers.rest;

import com.minibank.vo.AccountVO;
import com.minibank.vo.InlineObject;
import com.minibank.vo.TransactionVO;
import com.minibank.vo.UserVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminControllersRest implements AdminApi{

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
    public ResponseEntity<UserVO> getUserById(Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<AccountVO> updateStatusAcc(Long accountId, InlineObject inlineObject) {
        return null;
    }
}
