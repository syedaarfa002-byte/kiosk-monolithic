package com.smartserve.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartserve.auth.dto.AuthResponse;
import com.smartserve.auth.dto.LoginRequest;
import com.smartserve.auth.dto.RegisterRequest;
import com.smartserve.auth.entity.User;
import com.smartserve.auth.repository.UserRepository;
import com.smartserve.exception.InvalidCredentialsException;
import com.smartserve.exception.UserAlreadyExistsException;
import com.smartserve.security.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    
    
    @Override
    public AuthResponse register(RegisterRequest registerRequest) {

    	if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
    	    throw new UserAlreadyExistsException("Username already exists");
    	}  

        User user = new User();

        user.setUsername(registerRequest.getUsername());

        user.setPassword(
                passwordEncoder.encode(registerRequest.getPassword())
        );

        user.setRole(registerRequest.getRole());

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getUsername());

        AuthResponse response = new AuthResponse();

        response.setToken(token);
        response.setUsername(savedUser.getUsername());
        response.setRole(savedUser.getRole().name());

        return response;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

    	User user = userRepository
    	        .findByUsername(loginRequest.getUsername())
    	        .orElseThrow(() ->
    	                new InvalidCredentialsException("Invalid username or password"));

    	if (!passwordEncoder.matches(
    	        loginRequest.getPassword(),
    	        user.getPassword())) {

    	    throw new InvalidCredentialsException("Invalid username or password");
    	}

        String token = jwtUtil.generateToken(user.getUsername());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUsername(user.getUsername());
        response.setRole(user.getRole().name());

        return response;
    }
    
  
}