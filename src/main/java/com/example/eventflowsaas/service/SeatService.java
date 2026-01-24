package com.example.eventflowsaas.service;

import com.example.eventflowsaas.dto.SeatResponseDto;
import com.example.eventflowsaas.entity.Event;
import com.example.eventflowsaas.entity.Seat;
import com.example.eventflowsaas.entity.enums.SeatStatus;
import com.example.eventflowsaas.mapper.SeatMapper;
import com.example.eventflowsaas.repository.EventRepository;
import com.example.eventflowsaas.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final EventRepository eventRepository;
    private final SeatMapper seatMapper;

    public SeatService(SeatRepository seatRepository, EventRepository eventRepository, SeatMapper seatMapper) {
        this.seatRepository = seatRepository;
        this.eventRepository = eventRepository;
        this.seatMapper = seatMapper;
    }

    @Transactional
    public List<SeatResponseDto> generateSeatsForEvent(Long eventId, int rows, int setsPerRow, BigDecimal price){
        Event event = eventRepository.findById(eventId).orElseThrow();
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= rows; i++) {
            for (int i1 = 1; i1 <= setsPerRow; i1++) {
                Seat seat = new Seat();
                seat.setColumnNumber(i1);
                seat.setSeatNumber("R" + i + "-S" +i1);
                seat.setRowNumber(i);
                seat.setPrice(price);
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setEvent(event);
                seats.add(seat);
            }
        }
        event.setSeats(seats);
        List<Seat> saved = seatRepository.saveAll(seats);
        return seatMapper.toDtoList(saved);
    }
}
