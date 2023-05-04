package com.minibank.controllers;

import com.minibank.models.User;
import com.minibank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/success")
    public String successPage() {
        return "auth/success";
    }

    @GetMapping("/registration")
    public String registration () {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(User user) {
        User newUser = userService.create(user);
       return "redirect:auth/login";
    }
}
