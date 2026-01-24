package com.example.eventflowsaas.dto;

import com.example.eventflowsaas.entity.Tenant;
import com.example.eventflowsaas.entity.enums.UserRole;
import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String password;
    private UserRole role;
    private String email;
    private Tenant tenant;
}
