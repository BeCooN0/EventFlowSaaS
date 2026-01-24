package com.example.eventflowsaas.controller;

import com.example.eventflowsaas.dto.BookingRequestDto;
import com.example.eventflowsaas.dto.BookingResponseDto;
import com.example.eventflowsaas.service.BookingService;
import jakarta.servlet.http.PushBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private BookingService bookingService;
    @PostMapping("/add")
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto bookingRequestDto, PushBuilder pushBuilder){
        BookingResponseDto bookingResponseDto = bookingService.addBooking(bookingRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand(bookingResponseDto.getId())
                .expand("/id")
                .toUri();
        return ResponseEntity.created(location).body(bookingResponseDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id){
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }


}
