package com.example.eventflowsaas.entity;

import com.example.eventflowsaas.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class Booking extends BaseAuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private Long quantity;
    @Column(name = "user_id")
    private Long userId;
    @ManyToOne
    private Event event;
    @ManyToOne
    private Seat seat;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
