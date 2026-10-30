package com.smartserve.auth.dto;

import com.smartserve.auth.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String password;
    private Role role;
}