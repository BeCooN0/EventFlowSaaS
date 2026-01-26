package com.example.eventflowsaas.dto;

import lombok.Data;

@Data
public class TenantRequestDto {
    private String tenantName;
    private String identifier;
    private boolean status;
}
