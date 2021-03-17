package com.invicto.streaming_platform.web.controller;

import com.invicto.streaming_platform.persistence.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/")
public class UserController {

    @GetMapping("/signup")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        // TODO: save user to database via service

        return "redirect:/signup";
    }  

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/";
    }
}
