package com.example.eventflowsaas.entity;

import com.example.eventflowsaas.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    private Long id;
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserRole role;
    @Column(nullable = false)
    private String email;
    @ManyToOne
    private Tenant tenant;
}


