package com.example.eventflowsaas.dto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class BookingResponseDto {
    private Long id;
    private String createdAt;
    private BigDecimal price;
    private Long eventId;
    private Long userId;
    private Long quantity;
}
