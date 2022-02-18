package com.pentagon.cafe.virtualSmallJobFinder.payload;

import lombok.Data;

import java.util.Set;

@Data
public class JwtResponse {

    private String accessToken;
    private String refreshToken;
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;
    private final String type = "Bearer";

    public JwtResponse(String accessToken, String refreshToken, Long id, String username, String email, Set<String> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

}
