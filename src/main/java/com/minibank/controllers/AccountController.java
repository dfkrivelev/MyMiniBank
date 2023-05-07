package com.minibank.controllers;

import com.minibank.models.Account;
import com.minibank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/create")
    public String createPage(@ModelAttribute("account") Account account) {
        return "account/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("account") Account account,
                         @RequestParam("userId") int userId) {
        Account newAccount = accountService.create(account, userId);
        return "redirect:/account/ok";
    }

    @GetMapping("/ok")
    public String successPage() {
        return "account/ok";
    }
}
