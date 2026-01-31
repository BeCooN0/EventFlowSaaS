package com.example.eventflowsaas.dto;
import lombok.Data;

@Data
public class AuthenticationResponseDto {
    private final String token;

    public AuthenticationResponseDto(String token) {
        this.token = token;
    }
}
