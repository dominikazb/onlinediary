package com.dominikazb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @GetMapping("/demo")
    public String goToDemo() {
        return "demo";
    }

    @GetMapping("/diary/another")
    public String goToAnother() {
        return "another";
    }

}
