package com.invicto.streaming_platform.web.controller;

import com.invicto.streaming_platform.captcha.CaptchaService;
import com.invicto.streaming_platform.captcha.ReCaptchaInvalidException;
import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.services.UserService;
import com.invicto.streaming_platform.web.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CaptchaService captchaService;


    public UserController(UserService userService, PasswordEncoder passwordEncoder, CaptchaService captchaService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.captchaService = captchaService;
    }

    @GetMapping("/signup")
    public String registerUser(Model model) {
        model.addAttribute("user", new UserDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String addUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        try {
            String response = request.getParameter("g-recaptcha-response");
            captchaService.processResponse(response);
        } catch (ReCaptchaInvalidException e) {
            bindingResult.addError(new ObjectError("global", "Please verify you are not a robot by completing the captcha"));
            return "signup";
        }

        userService.createUser(convertDtoToUser(userDto));
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

    private User convertDtoToUser(UserDto dto) {
        String passwordHash = passwordEncoder.encode(dto.getPassword());
        return new User(dto.getLogin(), dto.getEmail(), passwordHash, dto.getDateOfBirth());
    }
}
