package com.salesmicroservices.sales.modules.jwt.service;

import com.salesmicroservices.sales.config.exception.ValidationException;
import com.salesmicroservices.sales.modules.jwt.dto.AuthUser;
import com.salesmicroservices.sales.modules.jwt.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthUser getAuthUser() {
        try {
            var userClaims = jwtTokenProvider
                .getClaims(jwtTokenProvider.getAccessToken());
            return AuthUser.convertFrom(userClaims.getBody());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ValidationException("There are no authenticated user.");
        }
    }
}
