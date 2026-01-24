package com.example.eventflowsaas.entity;

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
    @ManyToOne
    private User user;
    @ManyToOne
    private Event event;
    @ManyToOne
    private Seat seat;

}
