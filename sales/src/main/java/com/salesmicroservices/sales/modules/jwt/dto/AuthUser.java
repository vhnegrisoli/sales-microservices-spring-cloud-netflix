package com.salesmicroservices.sales.modules.jwt.dto;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {

    private Integer userId;
    private String username;
    private List<String> roles;

    public static AuthUser convertFrom(Claims userClaims) {
        return AuthUser
            .builder()
            .userId((Integer) userClaims.get("userId"))
            .username((String) userClaims.get("username"))
            .roles((ArrayList<String>) userClaims.get("roles"))
            .build();
    }
}
