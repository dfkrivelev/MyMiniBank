package com.minibank.controllers;

import com.minibank.models.Account;
import com.minibank.models.Transaction;
import com.minibank.models.User;
import com.minibank.services.AccountService;
import com.minibank.services.TransactionService;
import com.minibank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/trans")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService, AccountService accountService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping("/transfer/{id}")
    public String transactionPage(@ModelAttribute("transaction") Transaction transaction, @PathVariable("id") Long id,
                                  Model model) {
        User user = userService.getAuthUser();
        Account account = accountService.findById(id);

        model.addAttribute("user", user);
        model.addAttribute("account", account);
        return "trans/transfer";
    }

    @PostMapping("/transfer/{id}")
    public String transaction(@PathVariable("id") Long id,
                              @RequestParam("accountNumberTo") Long accountNumberTo,
                              @RequestParam("amount") Double amount,
                              @RequestParam("description") String description,
                              Model model) {
        User user = userService.getAuthUser();
        Account account = accountService.findById(id);
        List<Account> listAccount = user.getAccounts();

        model.addAttribute("account", account);
        transactionService.transfer(account.getAccountNumber(), accountNumberTo, amount, description);
        listAccount.replaceAll(s -> s.equals(account) ? s : accountService.findById(id));
        return "redirect:/account/myAccounts";
    }

    @GetMapping("/myTransfers/{id}")
    public String myTransfersPage(@PathVariable("id") Long id,
                                  Model model) {
        User user = userService.getAuthUser();
        Account account = accountService.findById(id);

        model.addAttribute("user", user);
        model.addAttribute("account", account);
        model.addAttribute("myTransfers", accountService.allTransactions(account));
        return "trans/myTransfers";
    }
}
