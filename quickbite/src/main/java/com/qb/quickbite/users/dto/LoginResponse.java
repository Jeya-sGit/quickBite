
package com.qb.quickbite.users.dto;

public class LoginResponse {

    private Long id;
    private String name;
    private String email;
    private String message;
    private String accessToken;

    public LoginResponse(Long id, String name, String email,
                         String message, String accessToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.message = message;
        this.accessToken = accessToken;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
