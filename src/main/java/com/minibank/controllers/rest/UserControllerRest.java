package com.minibank.controllers.rest;

import com.minibank.vo.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "User")
public class UserControllerRest implements UserApi{

    @Override
    public ResponseEntity<TransactionVO> createTransaction(Long accountId, InlineObject4 inlineObject4) {
        return null;
    }

    @Override
    public ResponseEntity<List<AccountVO>> getMyAccountList(Long userId, Integer page, Integer perPage) {
        return null;
    }

    @Override
    public ResponseEntity<List<TransactionVO>> getMyTransactionList(Long accountId, Integer page, Integer perPage) {
        return null;
    }
}
