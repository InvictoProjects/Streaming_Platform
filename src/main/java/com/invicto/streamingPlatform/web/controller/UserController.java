package com.invicto.streamingPlatform.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/";
    }
}
