package com.salesmicroservices.product.modules.jwt.service;

import com.salesmicroservices.product.config.exception.ValidationException;
import com.salesmicroservices.product.modules.jwt.dto.AuthUser;
import com.salesmicroservices.product.modules.jwt.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthUser getAuthUser() {
        try {
            var token = jwtTokenProvider.getAccessToken();
            var userClaims = jwtTokenProvider.getClaims(token);
            return AuthUser.convertFrom(userClaims.getBody());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ValidationException("There are no authenticated user.");
        }
    }
}
