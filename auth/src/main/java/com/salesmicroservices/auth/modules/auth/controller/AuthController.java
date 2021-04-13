package com.salesmicroservices.auth.modules.auth.controller;

import com.salesmicroservices.auth.modules.auth.dto.AuthRequest;
import com.salesmicroservices.auth.modules.auth.dto.AuthResponse;
import com.salesmicroservices.auth.modules.auth.dto.AuthUser;
import com.salesmicroservices.auth.modules.auth.service.AuthService;
import com.salesmicroservices.auth.modules.auth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/token")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthUserService authUserService;

    @PostMapping
    public AuthResponse getAuthorizationToken(@RequestBody AuthRequest request) {
        return authService.getAuthorizationToken(request);
    }

    @GetMapping("authorized")
    public ResponseEntity<HashMap<String, Object>> checkAuthorized() {
        var response = new HashMap<String, Object>();
        response.put("value", "You are authenticated!");
        response.put("status", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @GetMapping("auth-user")
    public AuthUser getAuthUser() {
        return authUserService.getAuthUser();
    }
}
