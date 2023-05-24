package com.minibank.controllers;

import com.minibank.models.Account;
import com.minibank.models.User;
import com.minibank.services.AccountService;
import com.minibank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;


    @Autowired
    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createPage(@ModelAttribute("account") Account account) {
        return "account/create";
    }

    @PostMapping("/create")
    public String create(@RequestParam("accountNumber") Long accountNumber,
                         @RequestParam("userId") Long userId) {
        accountService.create(accountNumber, userId);
        return "redirect:/account/ok";
    }

    @GetMapping("/ok")
    public String successPage() {
        return "account/ok";
    }

    @GetMapping("/myAccounts")
    public String myAccountsPage(Model model) {
        User user = userService.getAuthUser();
        List<Account> myAcc = userService.getMyAccount(user);

        model.addAttribute("user", user);
        model.addAttribute("myAcc", myAcc);
        return "account/myAccounts";
    }
}
