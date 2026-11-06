package com.qb.quickbite.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class UserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Pattern(
    regexp = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|outlook\\.com|hotmail\\.com|icloud\\.com)$",
    flags = Pattern.Flag.CASE_INSENSITIVE,
    message = "Only personal email providers such as Gmail, Yahoo, Outlook, iCloud and are allowed"
    )
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    @Pattern(
    regexp = ".*[a-z].*",
    message = "Password must contain at least one lowercase letter"
    )
    @Pattern(
    regexp = ".*[A-Z].*",
    message = "Password must contain at least one uppercase letter"
    )
    @Pattern(
    regexp = ".*\\d.*",
    message = "Password must contain at least one number"
    )
    @Pattern(
    regexp = ".*[@$!%*?&].*",
    message = "Password must contain at least one special character"
    )
    @Pattern(
    regexp = "^\\S+$",
    message = "Password must not contain spaces"
    )
    private String password;

    private String phone;

    public UserRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
