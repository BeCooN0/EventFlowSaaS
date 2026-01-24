package com.example.eventflowsaas.mapper;

import com.example.eventflowsaas.dto.UserRequestDto;
import com.example.eventflowsaas.dto.UserResponseDto;
import com.example.eventflowsaas.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequestDto userRequestDto);
    UserResponseDto toDto(User user);
    @Mapping(target = "user.password", ignore = true)
    void toUpdate(UserRequestDto userRequestDto, @MappingTarget User user);
}
