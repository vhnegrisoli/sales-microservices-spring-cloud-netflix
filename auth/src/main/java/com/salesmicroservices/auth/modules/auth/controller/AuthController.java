package com.salesmicroservices.auth.modules.auth.controller;

import com.salesmicroservices.auth.modules.auth.dto.AuthRequest;
import com.salesmicroservices.auth.modules.auth.dto.AuthResponse;
import com.salesmicroservices.auth.modules.auth.service.AuthService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public AuthResponse getAuthorizationToken(@RequestBody AuthRequest request) {
        return authService.getAuthorizationToken(request);
    }

    @GetMapping("authorized")
    public String checkAuthorized() {
        return "Ok";
    }
}
