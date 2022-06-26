package net.openwebinars.springboot.multitenant.app.multitenant.controller;

import lombok.RequiredArgsConstructor;
import net.openwebinars.springboot.multitenant.app.multitenant.tenant.dto.TenantDTO;
import net.openwebinars.springboot.multitenant.app.multitenant.tenant.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tenant")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;


    @PostMapping("/")
    public ResponseEntity<TenantDTO> createNewTenant(@RequestBody TenantDTO tenantDTO) {
        TenantDTO created = tenantService.createNewTenant(tenantDTO);

        URI createdURI = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(created.getTenantId()).toUri();


        return ResponseEntity
                .created(createdURI)
                .body(created);

    }

}
