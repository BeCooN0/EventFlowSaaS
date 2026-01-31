package com.example.eventflowsaas.service;

import com.example.eventflowsaas.dto.SeatResponseDto;
import com.example.eventflowsaas.entity.Event;
import com.example.eventflowsaas.entity.Seat;
import com.example.eventflowsaas.entity.enums.SeatStatus;
import com.example.eventflowsaas.repository.EventRepository;
import com.example.eventflowsaas.repository.SeatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private SeatService service;
    private Seat seat = new Seat();
    private Event event = new Event();

    @BeforeEach
    void signUp(){
        event.setId(1L);
        event.setAddress("ahucn");
        event.setTitle("seats");

        seat.setSeatNumber("sjkcs7e");
        seat.setPrice(BigDecimal.TWO);
        seat.setId(1L);
        seat.setColumnNumber(2L);
        seat.setRowNumber(3L);
        seat.setSeatStatus(SeatStatus.AVAILABLE);
    }

    @Test
    public void addSeat(){
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(seatRepository.save(ArgumentMatchers.any(Seat.class))).thenReturn(seat);
        when(eventRepository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);

        List<SeatResponseDto> result = service.generateSeatsForEvent(1L, 1L, 2L, BigDecimal.valueOf(10));

        Assertions.assertNotNull(result);
        verify(seatRepository, times(1)).save(ArgumentMatchers.any(Seat.class));
        verify(eventRepository, times(1)).save(ArgumentMatchers.any(Event.class));

    }

}
