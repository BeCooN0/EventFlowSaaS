package com.example.eventflowsaas.repository;

import com.example.eventflowsaas.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    @Query("select t.identifier from Tenant t")
    List<String> findAllIds();

}
