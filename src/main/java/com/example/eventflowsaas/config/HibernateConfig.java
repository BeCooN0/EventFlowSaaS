package com.example.eventflowsaas.config;

import com.example.eventflowsaas.security.ConnectionProvider;
import com.example.eventflowsaas.security.IdentifierResolver;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HibernateConfig {
    public LocalContainerEntityManagerFactoryBean factoryBean(DataSource dataSource, ConnectionProvider provider, IdentifierResolver identifierResolver){
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);
        bean.setPackagesToScan("com.example.eventflowsaas.entity");
        Map<String, Object> props = new HashMap<>();
        props.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, provider);
        props.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, identifierResolver);
        props.put(Environment.MULTI_TENANT_SCHEMA_MAPPER, "SCHEMA");
        bean.setJpaPropertyMap(props);
        return bean;
    }
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager();
    }
}