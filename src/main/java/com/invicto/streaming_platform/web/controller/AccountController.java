package com.invicto.streaming_platform.web.controller;

import com.invicto.streaming_platform.persistence.model.User;
import com.invicto.streaming_platform.services.UserService;
import com.invicto.streaming_platform.web.config.EmailConfig;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping(value = "/")
public class AccountController {

    private final JavaMailSenderImpl mailSender;
    private final EmailConfig emailConfig;
    private final UserService userService;

    public AccountController(JavaMailSenderImpl mailSender, EmailConfig emailConfig, UserService userService) {
        this.mailSender = mailSender;
        this.emailConfig = emailConfig;
        this.userService = userService;
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        try {
            userService.updateResetPasswordToken(token, email);
            String siteURL = request.getRequestURL().toString();
            String resetPasswordLink = siteURL.replace(request.getServletPath(), "") + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "forgot_password";
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        mailSender.setHost(emailConfig.getHost());
        mailSender.setPort(emailConfig.getPort());
        mailSender.setUsername(emailConfig.getUsername());
        mailSender.setPassword(emailConfig.getPassword());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("streaming1platform@gmail.com", "Streaming Platform Support");
        helper.setTo(recipientEmail);
        String subject = "Here's the link to reset your password";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.findByResetPasswordToken(token);
        if (user == null) {
            return "error";
        }
        model.addAttribute("token", token);
        model.addAttribute("message", "Invalid Token");
        return "reset_password";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String token = request.getParameter("token");
        String password = passwordEncoder.encode(request.getParameter("password"));

        User user = userService.findByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");
        userService.updatePasswordHash(user, password);
        model.addAttribute("message", "You have successfully changed your password.");
        return "message";
    }
}
