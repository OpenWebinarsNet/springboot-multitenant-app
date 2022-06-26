package net.openwebinars.springboot.multitenant.app.multitenant.tenant.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "public")
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Tenant {

    @Id
    @Column(name = "tenant_id")
    private String tenantID;

    @Column(name = "schema_name")
    private String schemaName;

}
