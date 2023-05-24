package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.User;
import com.minibank.models.constants.Country;
import com.minibank.models.constants.Status;
import com.minibank.models.constants.TypeTransfer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
@Transactional
@SpringBootTest
public class TransactionServiceTest {

    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Autowired
    public TransactionServiceTest(UserService userService, AccountService accountService, TransactionService transactionService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    private static final User user = new User("Test", "Testov", Country.POLAND,
            4845215L, "test@mail.com", "test");
    private static final User user1 = new User("Test1", "Testovicz", Country.POLAND,
            48452125L, "test2@mail.com", "test2");

    @Test
    public void testTransfer(){
        User newUser = userService.registration(user);
        User newUser1 = userService.registration(user1);

        Account fromUser = accountService.changeStatus(newUser.getAccounts().get(0), Status.ACTIVE);
        Account toUser = accountService.changeStatus(newUser1.getAccounts().get(0), Status.ACTIVE);

        transactionService.transfer(fromUser.getAccountNumber(), toUser.getAccountNumber(), 100.00, "");

        List<Transaction> expense = accountService.expenseTransactions(fromUser);
        List<Transaction> income = accountService.incomeTransactions(toUser);

        Assertions.assertEquals(2, transactionService.findAll().size());
        Assertions.assertEquals(1, expense.size());
        Assertions.assertEquals(1, income.size());
        Assertions.assertEquals(TypeTransfer.EXPENSE, expense.get(0).getTypeTransfer());
        Assertions.assertEquals(TypeTransfer.INCOME, income.get(0).getTypeTransfer());
    }
}
