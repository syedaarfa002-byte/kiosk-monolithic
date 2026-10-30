package com.smartserve.auth.service;

import com.smartserve.auth.dto.AuthResponse;
import com.smartserve.auth.dto.LoginRequest;
import com.smartserve.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse register(RegisterRequest registerRequest);
}