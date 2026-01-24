package com.example.eventflowsaas.security;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class IdentifierResolver implements CurrentTenantIdentifierResolver<String> {
    private final CurrentTenant currentTenant;

    public IdentifierResolver(CurrentTenant currentTenant) {
        this.currentTenant = currentTenant;
    }


    @Override
    public String resolveCurrentTenantIdentifier() {
        return currentTenant.toString();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public boolean isRoot(String tenantId) {
        return CurrentTenantIdentifierResolver.super.isRoot(tenantId);
    }
}
