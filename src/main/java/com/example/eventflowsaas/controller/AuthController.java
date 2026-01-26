package com.example.eventflowsaas.controller;

import com.example.eventflowsaas.dto.AuthenticationResponseDto;
import com.example.eventflowsaas.dto.UserRequestDto;
import com.example.eventflowsaas.dto.UserResponseDto;
import com.example.eventflowsaas.service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto register = authenticationService.registerUser(userRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/{id}")
                .buildAndExpand(register.getId())
                .toUri();
        return ResponseEntity.created(location).body(register);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody UserRequestDto userRequestDto) {
        AuthenticationResponseDto login = authenticationService.login(userRequestDto);
        return ResponseEntity.ok(login);
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        authenticationService.logout(header);
        return ResponseEntity.noContent().build();
    }
}
