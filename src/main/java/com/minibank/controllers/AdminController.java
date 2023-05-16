package com.minibank.controllers;

import com.minibank.models.Account;
import com.minibank.models.User;
import com.minibank.services.AccountService;
import com.minibank.services.TransactionService;
import com.minibank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")

public class AdminController {


    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Autowired
    public AdminController(UserService userService, AccountService accountService, TransactionService transactionService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        User user = userService.getAuthUser();

        model.addAttribute("user", user);
        return "admin/main";
    }

    @GetMapping("/userList")
    public String userListPage(Model model) {
        User user = userService.getAuthUser();

        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", user);
        return "admin/userList";
    }

    @GetMapping("/accountList")
    public String accountListPage(Model model) {
        User user = userService.getAuthUser();

        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("user", user);
        return "admin/accountList";
    }

    @GetMapping("/transactionsList")
    public String transactionsListPage(Model model) {
        User user = userService.getAuthUser();

        model.addAttribute("transactions", transactionService.findAll());
        model.addAttribute("user", user);
        return "admin/transactionsList";
    }
}
