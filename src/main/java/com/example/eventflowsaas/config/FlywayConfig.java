package com.example.eventflowsaas.config;
import com.example.eventflowsaas.repository.TenantRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@Slf4j
public class FlywayConfig {
    private final DataSource dataSource;
    private final TenantRepository tenantRepository;
    public FlywayConfig(DataSource dataSource, TenantRepository tenantRepository) {
        this.dataSource = dataSource;
        this.tenantRepository = tenantRepository;
    }

    @PostConstruct
    public void flywayConfig(){
        Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/public")
                .schemas("public")
                .baselineOnMigrate(true)
                .load()
                .migrate();

        List<String> allIds = tenantRepository.findAllIds();
        for (String allId : allIds) {
            try {
                Flyway.configure()
                        .dataSource(dataSource)
                        .locations("classpath:db/migration/tenant")
                        .schemas(allId)
                        .baselineOnMigrate(true)
                        .load()
                        .migrate();
            }catch (Exception e){
                log.error("Error when migrating tenant ID:{}: {}", allId, e.getMessage());
            }
        }
    }
}
