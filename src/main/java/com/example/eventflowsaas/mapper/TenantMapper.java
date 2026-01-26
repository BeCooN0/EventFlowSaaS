package com.example.eventflowsaas.mapper;

import com.example.eventflowsaas.dto.TenantRequestDto;
import com.example.eventflowsaas.dto.TenantResponseDto;
import com.example.eventflowsaas.entity.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TenantMapper {
    TenantResponseDto toDto(Tenant tenant);
    TenantResponseDto toUpdate(TenantRequestDto dto, @MappingTarget Tenant tenant);
    Tenant toTenant(TenantRequestDto tenantRequestDto);

}
