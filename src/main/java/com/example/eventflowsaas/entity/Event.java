package com.example.eventflowsaas.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class Event extends BaseAuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Instant eventStartTime;
    @CreatedDate
    private Instant endTime;
    private String address;
    @ManyToOne(cascade = CascadeType.ALL)
    private User createdBy;
    private List<Seat> seats;
}
