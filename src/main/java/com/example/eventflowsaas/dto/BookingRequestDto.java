package com.example.eventflowsaas.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class BookingRequestDto {
    private BigDecimal price;
    private Long eventId;
    private Long userId;
    private Long quantity;
    private Instant createdAt;
    private Long seatId;
}
