package com.qb.quickbite.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Pattern(
        regexp = "^\\S+$",
        message = "Email must not contain spaces"
    )
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
        regexp = ".*\\S.*",
        message = "Password cannot contain only whitespace"
    )
    private String password;

    public LoginRequest() {
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
}