package com.example.eventflowsaas.controller;
import com.example.eventflowsaas.dto.SeatRequestDto;
import com.example.eventflowsaas.dto.SeatResponseDto;
import com.example.eventflowsaas.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/seats")
@RestController
public class SeatController {
    private final SeatService service;

    public SeatController(SeatService service) {
        this.service = service;
    }

    @PostMapping("/generate/{eventId}")
    public ResponseEntity<List<SeatResponseDto>> generateSeatFomEvent(@PathVariable Long eventId, @RequestParam SeatRequestDto dto){
        List<SeatResponseDto> seats = service.generateSeatsForEvent(eventId, dto.getRowNumber(), dto.getColumnNumber(), dto.getPrice());
        return ResponseEntity.ok(seats);
    }
}
