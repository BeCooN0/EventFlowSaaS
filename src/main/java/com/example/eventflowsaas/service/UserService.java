package com.example.eventflowsaas.service;

import com.example.eventflowsaas.dto.UserRequestDto;
import com.example.eventflowsaas.dto.UserResponseDto;
import com.example.eventflowsaas.entity.User;
import com.example.eventflowsaas.mapper.UserMapper;
import com.example.eventflowsaas.repository.UserRepository;
import com.example.eventflowsaas.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserResponseDto updateUser(UserRequestDto userRequestDto) {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = principal.getUser();

        if (userRequestDto.getEmail() != null) {
            throw new AccessDeniedException("You do not have permission to change email!");
        }

        userMapper.toUpdate(userRequestDto, user);

        if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isBlank()) {
            if (passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
                throw new RuntimeException("New password must be different from current password!");
            }
            user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        }

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}