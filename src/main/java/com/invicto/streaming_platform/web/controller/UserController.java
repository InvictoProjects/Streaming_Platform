package com.invicto.streaming_platform.web.controller;

import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.services.UserService;
import com.invicto.streaming_platform.web.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String registerUser(Model model) {
        model.addAttribute("user", new UserDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String addUser(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        return "redirect:/";
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
