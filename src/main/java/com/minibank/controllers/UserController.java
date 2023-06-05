package com.minibank.controllers;

import com.minibank.models.User;
import com.minibank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String homePage(Model model) {
        User user = userService.getAuthUser();

        model.addAttribute("user", user);
        return "user/home";
    }

    @GetMapping("/setPassword")
    public String setPasswordPage(Model model) {
        User user = userService.getAuthUser();

        model.addAttribute("user", user);
        return "user/setPassword";
    }

    @PostMapping("/setPassword")
    public String setPasswordPage(Model model, @RequestParam("oldPassword") String oldPassword,
                                  @RequestParam("newPassword") String newPassword) {
        User user = userService.getAuthUser();

        userService.setPassword(user, oldPassword, newPassword);
        model.addAttribute("user", user);

        SecurityContextHolder.clearContext();
        return "redirect:/auth/login";
    }
}
