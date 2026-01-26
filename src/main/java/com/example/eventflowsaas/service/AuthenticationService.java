package com.example.eventflowsaas.service;

import com.example.eventflowsaas.security.JwtService;
import com.example.eventflowsaas.dto.AuthenticationResponseDto;
import com.example.eventflowsaas.dto.UserRequestDto;
import com.example.eventflowsaas.dto.UserResponseDto;
import com.example.eventflowsaas.entity.User;
import com.example.eventflowsaas.mapper.UserMapper;
import com.example.eventflowsaas.repository.UserRepository;
import com.example.eventflowsaas.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final BlockListTokenService blockListTokenService;

    public UserResponseDto registerUser(UserRequestDto userRequestDto){
        User user = userMapper.toUser(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public AuthenticationResponseDto login(UserRequestDto userRequestDto){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequestDto.getEmail(), userRequestDto.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();
        User user = userDetails.getUser();
        String tenantIdentifier = (user.getTenant() != null) ? user.getTenant().getIdentifier() : "public";
        String token = jwtService.generateToken(userDetails, tenantIdentifier);
        return new AuthenticationResponseDto(token);
    }
    public void logout(String token){
        if(token != null && token.startsWith("Bearer ")){
            token = token.substring(7) ;
            blockListTokenService.addTokenToBlockList(token);
            log.info("Token added to blocklist");
        }else {
            log.warn("Invalid token for logout");
        }
    }

}
