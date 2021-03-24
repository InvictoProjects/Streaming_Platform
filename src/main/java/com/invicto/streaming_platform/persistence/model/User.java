package com.invicto.streaming_platform.persistence.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "reset_psw_token")
    private String resetPasswordToken;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    public User() {}

    public User(String login, String email, String passwordHash, LocalDate dateOfBirth) {
        this.login = login;
        this.email = email;
        this.passwordHash = passwordHash;
        this.dateOfBirth = dateOfBirth;
    }

    public User(Long id, String login, String email, String passwordHash, LocalDate dateOfBirth) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.passwordHash = passwordHash;
        this.dateOfBirth = dateOfBirth;
    }

    public User(Long id, String login, String email, String passwordHash, String resetPasswordToken, LocalDate dateOfBirth) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.passwordHash = passwordHash;
        this.resetPasswordToken = resetPasswordToken;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
}
