package com.example.eventflowsaas.dto;

import com.example.eventflowsaas.entity.enums.SeatStatus;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SeatResponseDto {
    private Long id;
    private String seatNumber;
    private Long rowNumber;
    private Long columnNumber;
    private BigDecimal price;
    private SeatStatus seatStatus;
}
