package net.openwebinars.springboot.multitenant.app.multitenant.tenant.repo;

import net.openwebinars.springboot.multitenant.app.multitenant.tenant.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, String> {


}
