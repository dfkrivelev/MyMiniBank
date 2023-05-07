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

    @GetMapping("/transaction")
    public String transactionPage(@ModelAttribute("transaction")Transaction transaction){
        return "trans/transaction";
    }

    @PostMapping("/transaction")
    public String transaction(@ModelAttribute("transaction") Transaction transaction,
                              @RequestParam("accountFromId") int accountFromId,
                              @RequestParam("accountNumberTo") int accountNumberTo){

        return "redirect:/account/ok";
    }
}
