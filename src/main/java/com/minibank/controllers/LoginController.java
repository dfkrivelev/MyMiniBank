package com.minibank.controllers;

import com.minibank.models.User;
import com.minibank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(Model model){
        User user = userService.getAuthUser();

        model.addAttribute("user", user);
        return "auth/login";
    }

    @GetMapping("/success")
    public String successPage() {
        return "auth/success";
    }

    @GetMapping("/registration")
    public String registrationPage (@ModelAttribute("user") User user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        User newUser = userService.registration(user);
       return "redirect:/auth/login";
    }
}
