package net.openwebinars.springboot.multitenant.app.multitenant.tenant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Se crea esta clase, en lugar de añadir directamente schemaName a TenantDTO, en previsión
 * de que una versión diferente podría necesitar otros datos de conexión a la base de datos
 * para diferentes tenants (jdbc url, username, password, ...). Esto serviría para una arquitectura
 * multitenancy de tipo separated databases (una base de datos por tenant).
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSourceDTO {

    private String schemaName;

}
