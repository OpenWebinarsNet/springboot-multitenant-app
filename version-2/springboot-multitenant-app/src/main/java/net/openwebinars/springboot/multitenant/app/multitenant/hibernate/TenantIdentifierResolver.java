package net.openwebinars.springboot.multitenant.app.multitenant.hibernate;

import net.openwebinars.springboot.multitenant.app.multitenant.context.TenantContext;
import net.openwebinars.springboot.multitenant.app.multitenant.tenant.dto.TenantDTO;
import net.openwebinars.springboot.multitenant.app.multitenant.tenant.model.Tenant;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        //return Optional.ofNullable(TenantContext.getCurrentTenant())
        return getTenantIdentifier()
                .orElse(TenantContext.DEFAULT_TENANT_ID);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    public Optional<String> getTenantIdentifier() {
        return Optional
                .ofNullable(TenantContext.getCurrentTenant())
                .map(TenantDTO::getTenantId);
    }
}
