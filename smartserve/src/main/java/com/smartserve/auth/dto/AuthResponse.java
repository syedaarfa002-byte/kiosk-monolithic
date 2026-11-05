package com.smartserve.auth.dto;

import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private String username;
    private String role;

}