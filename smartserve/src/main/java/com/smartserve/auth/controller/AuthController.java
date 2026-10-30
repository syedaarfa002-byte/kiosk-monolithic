package com.smartserve.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartserve.auth.dto.AuthResponse;
import com.smartserve.auth.dto.LoginRequest;
import com.smartserve.auth.dto.RegisterRequest;
import com.smartserve.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest registerRequest) {

        AuthResponse response = authService.register(registerRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest loginRequest) {

        AuthResponse response = authService.login(loginRequest);

        return ResponseEntity.ok(response);
    }
}