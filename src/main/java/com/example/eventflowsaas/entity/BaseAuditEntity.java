package com.example.eventflowsaas.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data

public class BaseAuditEntity {
    private Instant createdAt;
    private Instant updatedAt;
}
