package com.salesmicroservices.auth.modules.auth.service;

import com.netflix.discovery.converters.Auto;
import com.salesmicroservices.auth.config.exception.NotFoundException;
import com.salesmicroservices.auth.modules.auth.dto.AuthRequest;
import com.salesmicroservices.auth.modules.auth.dto.AuthResponse;
import com.salesmicroservices.auth.modules.jwt.JwtTokenProvider;
import com.salesmicroservices.auth.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    public AuthResponse getAuthorizationToken(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        ));

        var user = userRepository.findByUserName(request.getUsername())
            .orElseThrow(() -> new NotFoundException("User not found."));

        var token = jwtTokenProvider.createToken(user, user.getRoles());

       return AuthResponse.create(user.getUsername(), token);
    }
}