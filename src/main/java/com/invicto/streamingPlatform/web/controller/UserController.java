package com.invicto.streamingPlatform.web.controller;

import com.invicto.streamingPlatform.persistence.model.User;
import com.invicto.streamingPlatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
