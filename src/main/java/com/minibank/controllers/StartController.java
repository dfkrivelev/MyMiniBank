package com.minibank.controllers;

import com.minibank.models.User;
import com.minibank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class StartController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String mainPage(Model model) {
        User user = userService.getAuthUser();

        model.addAttribute("user", user);
        return "main/index";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        User user = userService.getAuthUser();

        model.addAttribute("user", user);
        return "main/about";
    }

    @GetMapping("/technology")
    public String technologyPage(Model model) {
        User user = userService.getAuthUser();

        model.addAttribute("user", user);
        return "main/technology";
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        User user = userService.getAuthUser();

        model.addAttribute("user", user);
        return "main/contact";
    }

}
