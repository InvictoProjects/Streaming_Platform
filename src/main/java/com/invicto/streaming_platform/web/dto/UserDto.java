package com.invicto.streaming_platform.web.dto;

import com.invicto.streaming_platform.validation.PasswordMatches;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@PasswordMatches
public class UserDto {

    @Email
    @NotEmpty(message = "Email is required.")
    private String email;

    @NotEmpty(message = "Login is required.")
    private String login;

    @NotEmpty(message = "Password is required.")
    private String password;

    @Transient
    @NotEmpty(message = "Password confirmation is required.")
    private String passwordConfirmation;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    public UserDto() {
        super();
    }
    public UserDto(@Email @NotEmpty(message = "Email is required.") String email,
                   @NotEmpty(message = "Login is required.") String login,
                   @NotEmpty(message = "Password is required.") String password,
                   @NotEmpty(message = "Password confirmation is required.") String passwordConfirmation,
                   LocalDate dateOfBirth) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.dateOfBirth = dateOfBirth;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
