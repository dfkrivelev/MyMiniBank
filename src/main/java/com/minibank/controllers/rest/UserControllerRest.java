package com.minibank.controllers.rest;

import com.minibank.models.Account;
import com.minibank.models.User;
import com.minibank.security.SecurityUser;
import com.minibank.services.UserService;
import com.minibank.vo.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('CLIENT')")
@Tag(name = "User")
public class UserControllerRest implements UserApi{

    private final UserService userService;

    @Autowired
    public UserControllerRest(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<TransactionVO> createTransaction(Long accountId, InlineObject3 inlineObject3) {
        return null;
    }

    @Override
    @ResponseBody
    public ResponseEntity<List<AccountVO>> getMyAccountList(Authentication authentication, Integer page, Integer perPage) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        List<Account> userAccount = securityUser.getUser().getAccounts();

        List<AccountVO> accountVOList = userAccount.stream().map(AccountVO::valueOf).toList();

        return ResponseEntity.ok(accountVOList);
    }

    @Override
    public ResponseEntity<List<TransactionVO>> getMyTransactionList(Long accountId, Integer page, Integer perPage) {
        return null;
    }
}
