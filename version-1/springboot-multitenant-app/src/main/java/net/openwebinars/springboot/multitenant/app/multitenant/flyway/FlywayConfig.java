package net.openwebinars.springboot.multitenant.app.multitenant.flyway;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FlywayConfig {

    private static final String DB_MIGRATION_TENANTS = "db/migration/tenants";

    private final Environment env;

    @PostConstruct
    void migrateAllTenants() {
        getAllTenants()
                .forEach(this::migrateForTenant);
    }

    private List<String> getAllTenants() {
        return List.of(
                "tenant1",
                "tenant2",
                "public"
        );
    }

    private void migrateForTenant(String tenantId) {
        Pair<String, HikariDataSource> data = dataSource(tenantId);
        Flyway flyway = Flyway.configure()
                .locations("classpath:"+DB_MIGRATION_TENANTS)
                .baselineOnMigrate(true)
                .validateMigrationNaming(true)
                .schemas(data.getFirst())
                .dataSource(data.getSecond())
                .load();

        flyway.repair();
        flyway.migrate();
    }

    public Pair<String, HikariDataSource> dataSource(String tenantId) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(env.getProperty(org.hibernate.cfg.Environment.DRIVER).toString());
        hikariConfig.setJdbcUrl(env.getProperty(org.hibernate.cfg.Environment.URL).toString());
        hikariConfig.setUsername(env.getProperty(org.hibernate.cfg.Environment.USER).toString());
        hikariConfig.setPassword(env.getProperty(org.hibernate.cfg.Environment.PASS).toString());
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return Pair.of(tenantId,  dataSource);
    }
}

