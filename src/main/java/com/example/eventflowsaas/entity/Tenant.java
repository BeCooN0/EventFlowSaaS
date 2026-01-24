package com.example.eventflowsaas.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenantName;
    @Column(unique = true, nullable = false)
    private String identifier;
    private boolean status;
}
