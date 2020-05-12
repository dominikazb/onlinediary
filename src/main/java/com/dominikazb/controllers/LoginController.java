package com.dominikazb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String goHome(Model model) {
        return "login";
    }

    @GetMapping("/login")
    public String goToLoginPage() {
        return "login";
    }

}