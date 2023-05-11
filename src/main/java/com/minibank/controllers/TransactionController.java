package com.minibank.controllers;

import com.minibank.models.Transaction;
import com.minibank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trans")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transfer")
    public String transactionPage(@ModelAttribute("transaction")Transaction transaction){
        return "trans/transfer";
    }

    @PostMapping("/transfer")
    public String transaction(@ModelAttribute("transaction") Transaction transaction,
                              @RequestParam("accountFromId") Long accountFromId,
                              @RequestParam("accountToId") Long accountToId){
        transactionService.transfer(transaction, accountFromId, accountToId);
        return "redirect:/account/ok";
    }
}
