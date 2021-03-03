package com.invicto.streamingPlatform.web.controller;

import com.invicto.streamingPlatform.persistence.model.User;
import com.invicto.streamingPlatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {
    @Autowired
    public UserService userService;

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Pattern emailPattern = Pattern.compile("A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(username);
        User user;
        if (matcher.find()) {
            user = userService.findByEmailAddress(username);
        } else {
            user = userService.findByLogin(username);
        }
        if (user != null) {
            return "redirect:/login?error";
        } else {
            // TODO: authorize user
            return "redirect:/";
        }
    }
}
