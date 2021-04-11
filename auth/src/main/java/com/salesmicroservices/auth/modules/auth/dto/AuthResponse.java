package com.salesmicroservices.auth.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String username;

    private String token;

    public static AuthResponse create(String username, String token) {
        return AuthResponse
            .builder()
            .username(username)
            .token(token)
            .build();
    }
}
