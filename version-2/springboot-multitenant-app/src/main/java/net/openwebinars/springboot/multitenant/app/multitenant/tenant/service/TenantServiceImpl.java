package net.openwebinars.springboot.multitenant.app.multitenant.tenant.service;

import lombok.RequiredArgsConstructor;
import net.openwebinars.springboot.multitenant.app.multitenant.context.TenantContext;
import net.openwebinars.springboot.multitenant.app.multitenant.flyway.FlywayBuilder;
import net.openwebinars.springboot.multitenant.app.multitenant.tenant.dto.TenantDTO;
import net.openwebinars.springboot.multitenant.app.multitenant.tenant.dto.TenantDTOConverter;
import net.openwebinars.springboot.multitenant.app.multitenant.tenant.repo.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository repository;
    private final TenantDTOConverter converter;
    private final FlywayBuilder flywayBuilder;

    @Override
    public TenantDTO findByTenantId(String tenantId) {
        return repository.findById(tenantId)
                .map(converter::convertTenantToTenantDTO)
                .orElseThrow(() -> new RuntimeException(String.format("Uknown tenantId: %s", tenantId)));
    }

    @Override
    public boolean setTenantInContext(String tenantId) {
        TenantDTO t = findByTenantId(tenantId);
        TenantContext.setCurrentTenant(t);
        return true;
    }

    @Override
    public List<TenantDTO> getAllTenants() {
        return repository.findAll()
                .stream()
                .map(converter::convertTenantToTenantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TenantDTO createNewTenant(TenantDTO tenantDTO) {
        return Optional
                .ofNullable(tenantDTO)
                .map(this::buildDatabaseSchema)
                .map(converter::convertTenantDTOToTenant)
                .map(repository::save)
                .map(converter::convertTenantToTenantDTO)
                .orElseThrow(() -> new RuntimeException("Error adding new tenant"));
    }

    private TenantDTO buildDatabaseSchema(TenantDTO tenantDTO) {
        flywayBuilder
                .createFlyway(tenantDTO)
                .migrate();
        return tenantDTO;

    }


}
