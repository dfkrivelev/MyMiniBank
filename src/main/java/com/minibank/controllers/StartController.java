package com.minibank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class StartController {

    @GetMapping("/")
    public String mainPage(Model model) {
        return "main/index";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        return "about";
    }


}
