package com.example.eventflowsaas.dto;

import com.example.eventflowsaas.entity.Tenant;
import com.example.eventflowsaas.entity.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank(message = "Username is required!")
    private String username;
    @NotBlank(message = "Password is required!")
    @Size(min = 10, message = "Password must be at least 10 characters long")
    private String password;
    private UserRole role;
    @Email(message = "Please provide an valid email address!")
    @NotBlank(message = "Email is required!")
    private String email;
    private Tenant tenant;
}
