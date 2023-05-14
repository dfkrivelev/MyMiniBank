package com.minibank.controllers;

import com.minibank.models.User;
import com.minibank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/main")
    public String mainPage(Model model) {
        User user = userService.getAuthUser();

        model.addAttribute("user", user);
        return "admin/main";
    }

}
