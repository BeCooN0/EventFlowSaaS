package com.example.eventflowsaas.service;

import com.example.eventflowsaas.dto.TenantRequestDto;
import com.example.eventflowsaas.dto.TenantResponseDto;
import com.example.eventflowsaas.entity.Tenant;
import com.example.eventflowsaas.mapper.TenantMapper;
import com.example.eventflowsaas.repository.TenantRepository;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final DataSource dataSource;
    public TenantService(TenantRepository tenantRepository, TenantMapper tenantMapper, DataSource dataSource) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
        this.dataSource = dataSource;
    }
    public TenantResponseDto addTenant(TenantRequestDto tenantRequestDto){
        Tenant tenant = tenantMapper.toTenant(tenantRequestDto);
        tenant.setStatus(true);
        tenantRepository.save(tenant);
        try {
            Flyway.configure()
                    .dataSource(dataSource)
                    .locations("/db/migration/tenant")
                    .schemas(tenant.getIdentifier())
                    .baselineOnMigrate(true)
                    .load()
                    .migrate();
        }catch (Exception e){
            e.getMessage();
        }
        return tenantMapper.toDto(tenant);
    }
}
