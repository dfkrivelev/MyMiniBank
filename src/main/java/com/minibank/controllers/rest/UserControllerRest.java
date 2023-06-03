package com.minibank.controllers.rest;

import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.User;
import com.minibank.models.comparator.ComparatorDate;
import com.minibank.security.SecurityUser;
import com.minibank.services.AccountService;
import com.minibank.services.TransactionService;
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

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@PreAuthorize("hasAuthority('CLIENT')")
@Tag(name = "User")
public class UserControllerRest implements UserApi{

    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Autowired
    public UserControllerRest(UserService userService,AccountService accountService, TransactionService transactionService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public ResponseEntity<TransactionVO> createTransaction(Authentication authentication, InlineObject3 inlineObject3) {
        SecurityUser securityUser = userService.getAuthUser(authentication);

        Account account = accountService.findByAccountNumber(inlineObject3.getAccountFrom());

        if(securityUser.getUser().getAccounts().stream().anyMatch(acc -> acc.getAccountNumber().equals(account.getAccountNumber()))){
            transactionService.transfer(account.getAccountNumber(), inlineObject3.getAccountTo(), inlineObject3.getAmount(),
                    inlineObject3.getDescription());
            if(inlineObject3.getAccountFrom().equals(inlineObject3.getAccountTo())){
                List<Transaction> incomeTransaction = accountService.incomeTransactions(account);
                incomeTransaction.sort(new ComparatorDate());

                return ResponseEntity.ok(TransactionVO.valueOf(incomeTransaction.get(incomeTransaction.size() - 1)));
            }

            List<Transaction> expenseTransfer = accountService.expenseTransactions(account);
            expenseTransfer.sort(new ComparatorDate());

            return ResponseEntity.ok(TransactionVO.valueOf(expenseTransfer.get(expenseTransfer.size() - 1)));
        }else {
            throw new RuntimeException("User does not have such an account");
        }
    }

    @Override
    @ResponseBody
    public ResponseEntity<List<AccountVO>> getMyAccountList(Authentication authentication, Integer page, Integer perPage) {
        SecurityUser securityUser = userService.getAuthUser(authentication);

        List<Account> userAccount = securityUser.getUser().getAccounts();

        List<AccountVO> accountVOList = userAccount.stream().map(AccountVO::valueOf).toList();

        return ResponseEntity.ok(accountVOList);
    }

    @Override
    public ResponseEntity<List<TransactionVO>> getMyTransactionList(Authentication authentication ,Long accountNumber,
                                                                    Integer page, Integer perPage) {

        SecurityUser securityUser = userService.getAuthUser(authentication);

        Account account = accountService.findByAccountNumber(accountNumber);

        if(securityUser.getUser().getAccounts().stream().anyMatch(acc -> acc.getAccountNumber().equals(account.getAccountNumber()))){
            List<Transaction> myTransaction = accountService.allTransactions(account);
            List<TransactionVO> transactionVOList = myTransaction.stream().map(TransactionVO::valueOf).toList();

            return ResponseEntity.ok(transactionVOList);
        }else {
           throw new RuntimeException("User does not have such an account");
        }
    }
}
