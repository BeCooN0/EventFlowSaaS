package com.example.eventflowsaas.dto;
import lombok.Data;
import java.time.Instant;

@Data
public class EventRequestDto {
    private String address;
    private String title;
    private Instant startedAt;
    private Instant endedAt;
    private Long createdById;
}
