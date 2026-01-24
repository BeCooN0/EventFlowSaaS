package com.example.eventflowsaas.entity;

import com.example.eventflowsaas.entity.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @ManyToOne
    private Event event;
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;
    private int rowNumber;
    private int columnNumber;
    private String seatNumber;
    private BigDecimal price;

}
