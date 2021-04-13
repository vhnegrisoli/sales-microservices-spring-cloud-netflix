package com.salesmicroservices.sales.modules.jwt.service;

import com.salesmicroservices.sales.config.exception.NotFoundException;
import com.salesmicroservices.sales.modules.jwt.dto.AuthUser;
import com.salesmicroservices.sales.modules.jwt.provider.JwtTokenProvider;
import com.salesmicroservices.sales.modules.jwt.util.RequestUtil;
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
