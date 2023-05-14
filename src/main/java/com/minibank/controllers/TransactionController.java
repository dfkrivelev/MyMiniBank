package com.minibank.controllers;

import com.minibank.models.Transaction;
import com.minibank.models.User;
import com.minibank.services.TransactionService;
import com.minibank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trans")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/transfer")
    public String transactionPage(@ModelAttribute("transaction")Transaction transaction){
        return "trans/transfer";
    }

    @PostMapping("/transfer")
    public String transaction(@ModelAttribute("transaction") Transaction transaction,
                              @RequestParam("accountToId") Long accountToId){
        User user = userService.getAuthUser();


        transactionService.transfer(transaction, accountFromId, accountToId);
        return "redirect:/account/ok";
    }
}
