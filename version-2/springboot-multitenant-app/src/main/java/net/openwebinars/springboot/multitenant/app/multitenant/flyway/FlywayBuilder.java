package net.openwebinars.springboot.multitenant.app.multitenant.flyway;


import lombok.AllArgsConstructor;
import net.openwebinars.springboot.multitenant.app.multitenant.tenant.dto.DataSourceDTO;
import net.openwebinars.springboot.multitenant.app.multitenant.tenant.dto.TenantDTO;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.util.Optional;

@AllArgsConstructor
public class FlywayBuilder {

    private static final String DEFAULT_SCHEMA_LOCATION = "/db/migration/default";
    private static final String TENANT_SCHEMA_LOCATION = "/db/migration/tenant";

    private final DataSource dataSource;

    /**
     * Para mirgrar el esquema por defecto, de gestión de tenants
     * @param schemaName
     * @return
     */
    Flyway createFlyway(String schemaName) {
       return Flyway
               .configure()
               .dataSource(dataSource)
               .locations(DEFAULT_SCHEMA_LOCATION)
               .schemas(schemaName)
               .load();
    }

    /**
     * Para migrar el esquema de cada tenant
     * @param tenantDTO
     * @return
     */
    public Flyway createFlyway(TenantDTO tenantDTO) {
        return Flyway
                .configure()
                .dataSource(dataSource)
                .locations(TENANT_SCHEMA_LOCATION)
                .schemas(getSchemaName(tenantDTO))
                .load();
    }

    private String getSchemaName(TenantDTO tenantDTO) {
        return Optional
                .ofNullable(tenantDTO)
                .map(TenantDTO::getDataSourceDTO)
                .map(DataSourceDTO::getSchemaName)
                .orElseThrow(() -> new RuntimeException("Tenant model without schema name"));
    }



}
