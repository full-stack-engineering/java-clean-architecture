package io.github.mat3e.dto;

public class AuthenticationRequestDto {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }
}
