package com.minibank.controllers;


import com.minibank.models.User;
import com.minibank.security.SecurityUser;
import com.minibank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/homePage")
    public String homePage(Model model) {
        User user = userService.getAuthUser();

        model.addAttribute("user", user);
        return "user/homePage";
    }
}
