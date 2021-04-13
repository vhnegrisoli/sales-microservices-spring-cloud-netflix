package com.salesmicroservices.auth.modules.auth.service;

import com.salesmicroservices.auth.config.exception.NotFoundException;
import com.salesmicroservices.auth.modules.auth.dto.AuthUser;
import com.salesmicroservices.auth.modules.auth.util.RequestUtil;
import com.salesmicroservices.auth.modules.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthUser getAuthUser() {
        try {
            var currentRequest = RequestUtil.getCurrentRequest();
            var token = jwtTokenProvider.resolveToken(currentRequest);
            var userClaims = jwtTokenProvider.getClaims(token);
            return AuthUser.convertFrom(userClaims.getBody());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NotFoundException("There are no authenticated user.");
        }
    }
}
