package net.openwebinars.springboot.multitenant.app.multitenant.tenant.service;

import net.openwebinars.springboot.multitenant.app.multitenant.tenant.dto.TenantDTO;
import net.openwebinars.springboot.multitenant.app.multitenant.tenant.model.Tenant;

import java.util.List;

public interface TenantService {

    TenantDTO findByTenantId(String tenantId);
    boolean setTenantInContext(String tenantId);
    List<TenantDTO> getAllTenants();
    TenantDTO createNewTenant(TenantDTO tenantDTO);


}
