package com.example.eventflowsaas.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.util.function.LongFunction;

@Data
public class BookingRequestDto {
    private BigDecimal price;
    private Long eventId;
    private Long userId;
    private Long quantity;
    private String createdAt;
    private Long seatId;
}
