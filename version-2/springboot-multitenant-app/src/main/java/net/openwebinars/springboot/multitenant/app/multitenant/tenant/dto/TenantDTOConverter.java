package net.openwebinars.springboot.multitenant.app.multitenant.tenant.dto;

import net.openwebinars.springboot.multitenant.app.multitenant.tenant.model.Tenant;
import org.springframework.stereotype.Component;

@Component
public class TenantDTOConverter {

    public TenantDTO convertTenantToTenantDTO(Tenant t) {
        return TenantDTO.builder()
                .tenantId(t.getTenantID())
                .dataSourceDTO(
                        DataSourceDTO.builder()
                                .schemaName(t.getSchemaName())
                                .build()
                )
                .build();
    }


    public Tenant convertTenantDTOToTenant(TenantDTO dto) {
        return Tenant.builder()
                .tenantID(dto.getTenantId())
                .schemaName(dto.getDataSourceDTO().getSchemaName())
                .build();
    }
}
