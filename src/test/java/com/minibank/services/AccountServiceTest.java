package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.User;
import com.minibank.models.constants.Country;
import com.minibank.models.constants.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
@Transactional
@SpringBootTest
public class AccountServiceTest {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public AccountServiceTest(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    private static final User user = new User("Test", "Testov", Country.POLAND,
            4845215L, "test@@mail.com", "test");

    @Test
    public void testCreate() {
        User newUser = userService.registration(user);

        Account newAccount = accountService.create(12345L, newUser.getId());

        Assertions.assertEquals(2, newUser.getAccounts().size());
        Assertions.assertEquals(Status.ACTIVE, newAccount.getStatus());
    }

    @Test
    public void testChangeStatus() {
        User newUser = userService.registration(user);
        Assertions.assertEquals(Status.BLOCK, newUser.getAccounts().get(0).getStatus());

        Account newAccount = accountService.changeStatus(newUser.getAccounts().get(0), Status.ACTIVE);

        Assertions.assertEquals(Status.ACTIVE, newAccount.getStatus());
    }
}
