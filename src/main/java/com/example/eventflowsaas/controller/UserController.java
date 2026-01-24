package com.example.eventflowsaas.controller;

import com.example.eventflowsaas.dto.AuthenticationResponseDto;
import com.example.eventflowsaas.dto.UserRequestDto;
import com.example.eventflowsaas.dto.UserResponseDto;
import com.example.eventflowsaas.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final AuthenticationService authenticationService;
    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/regsiter")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto register = authenticationService.registerUser(userRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                        .path("/{id}")
                .buildAndExpand(register.getId())
                .toUri();
        return ResponseEntity.created(location).body(register);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody UserRequestDto userRequestDto){
        AuthenticationResponseDto login = authenticationService.login(userRequestDto);
        return ResponseEntity.ok(login);
    }
}
