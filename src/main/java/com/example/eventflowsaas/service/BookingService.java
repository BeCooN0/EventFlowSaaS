package com.example.eventflowsaas.service;

import com.example.eventflowsaas.dto.BookingRequestDto;
import com.example.eventflowsaas.dto.BookingResponseDto;
import com.example.eventflowsaas.entity.Booking;
import com.example.eventflowsaas.entity.Event;
import com.example.eventflowsaas.entity.Seat;
import com.example.eventflowsaas.entity.User;
import com.example.eventflowsaas.entity.enums.SeatStatus;
import com.example.eventflowsaas.mapper.BookingMapper;
import com.example.eventflowsaas.repository.BookingRepository;
import com.example.eventflowsaas.repository.EventRepository;
import com.example.eventflowsaas.repository.SeatRepository;
import com.example.eventflowsaas.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final SeatRepository seatRepository;
    private final EventRepository eventRepository;
    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper, SeatRepository seatRepository, EventRepository eventRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.seatRepository = seatRepository;
        this.eventRepository = eventRepository;
    }


    @Transactional
    public BookingResponseDto addBooking(BookingRequestDto bookingRequestDto) {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();

        Seat seat = seatRepository.findById(bookingRequestDto.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.getSeatStatus() != SeatStatus.AVAILABLE) {
            throw new RuntimeException("this is already Booking");
        }

        Event event = eventRepository.findById(bookingRequestDto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSeat(seat);
        booking.setEvent(event);
        booking.setPrice(seat.getPrice());
        booking.setCreatedAt(Instant.now());
        seat.setSeatStatus(SeatStatus.SOLD);

        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toDto(saved);
    }


    public void cancelBooking(Long bookingId){
        try {
            bookingRepository.deleteById(bookingId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
