package com.example.eventflowsaas.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class EventResponseDto {
    private Long id;
    private String title;
    private String address;
    private Instant startedAt;
    private Instant endedAt;
    private Long createdById;
}
