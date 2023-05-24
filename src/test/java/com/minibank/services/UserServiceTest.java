package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.User;
import com.minibank.models.constants.Country;
import com.minibank.models.constants.Status;
import com.minibank.models.constants.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
@Transactional
@SpringBootTest
public class UserServiceTest {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public UserServiceTest(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    private static final User user = new User("Test", "Testov", Country.POLAND,
            4845215L, "test@@mail.com", "test");

    @Test
    public void testRegistration(){
        List<User> userList = userService.findAll();
        int count = userList.size();

        User newUser = userService.registration(user);

        userList = userService.findAll();
        Assertions.assertEquals(1, userList.size() - count);
        Assertions.assertEquals("Test", newUser.getFirstName());
        Assertions.assertEquals(UserRole.CLIENT, newUser.getRole());
        Assertions.assertEquals(Status.ACTIVE, newUser.getStatus());
    }

    @Test
    public void testCreteAccountAuto(){
        User newUser = userService.registration(user);
        List<Account> userAccounts = newUser.getAccounts();

        List<Account> allAccounts = accountService.findAll();

        Assertions.assertEquals(1, userAccounts.size());
        Assertions.assertEquals(1, allAccounts.size());
        Assertions.assertEquals(Status.BLOCK, allAccounts.get(0).getStatus());
    }

    @Test
    public void testCreateAdmin(){
        User newAdmin = userService.createAdmin(user);
        List<User> allUsers = userService.findAll();

        Assertions.assertEquals(1, allUsers.size());
        Assertions.assertEquals(UserRole.ADMIN, newAdmin.getRole());
    }
}
