package net.openwebinars.springboot.multitenant.app.multitenant.tenant.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantDTO {

    private String tenantId;
    private DataSourceDTO dataSourceDTO;


}
