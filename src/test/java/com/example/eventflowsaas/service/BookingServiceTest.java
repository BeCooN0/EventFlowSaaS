package com.example.eventflowsaas.service;

import com.example.eventflowsaas.dto.BookingRequestDto;
import com.example.eventflowsaas.dto.BookingResponseDto;
import com.example.eventflowsaas.entity.Booking;
import com.example.eventflowsaas.entity.Seat;
import com.example.eventflowsaas.repository.BookingRepository;
import com.example.eventflowsaas.repository.SeatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private BookingService bookingService;
    private BookingRequestDto dto;
    private Booking saved;
    @Mock
    private SeatRepository seatRepository;

    @BeforeEach
    void signUp() {

        dto = new BookingRequestDto();
        dto.setPrice(BigDecimal.valueOf(1L));
        dto.setQuantity(10L);
        dto.setUserId(1L);
        dto.setSeatId(1L);

        saved = new Booking();
        saved.setPrice(BigDecimal.valueOf(10));
        saved.setId(1L);
        saved.setQuantity(10L);
        saved.setUserId(1L);
        saved.setSeat(new Seat());
    }

    @Test
    public void addBooking(){
        when(bookingRepository.save(any(Booking.class))).thenReturn(saved);
        when(seatRepository.findById(1L)).thenReturn(Optional.of(new Seat()));
        BookingResponseDto result = bookingService.addBooking(dto);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(saved.getQuantity(), result.getQuantity());
        verify(seatRepository).save(any(Seat.class));
        verify(bookingRepository, times(1));
    }

}
