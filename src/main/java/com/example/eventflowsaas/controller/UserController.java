package com.example.eventflowsaas.controller;

import com.example.eventflowsaas.dto.UserRequestDto;
import com.example.eventflowsaas.dto.UserResponseDto;
import com.example.eventflowsaas.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.updateUser(userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }



}
