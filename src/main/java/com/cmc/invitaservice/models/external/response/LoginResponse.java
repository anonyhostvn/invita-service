package com.cmc.invitaservice.models.external.response;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String refreshToken;
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private  String email;
    private List<String> roles;

    public LoginResponse(String refreshToken, String token, Long id, String username, String email, List<String> roles) {
        this.refreshToken = refreshToken;
        this.token = token;
        this.id = id;
        this.username =username;
        this.email = email;
        this.roles = roles;
    }
}
