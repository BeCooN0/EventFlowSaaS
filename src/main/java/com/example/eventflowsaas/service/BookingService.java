package com.example.eventflowsaas.service;

import com.example.eventflowsaas.dto.BookingRequestDto;
import com.example.eventflowsaas.dto.BookingResponseDto;
import com.example.eventflowsaas.entity.Booking;
import com.example.eventflowsaas.entity.Event;
import com.example.eventflowsaas.entity.Seat;
import com.example.eventflowsaas.entity.User;
import com.example.eventflowsaas.entity.enums.BookingStatus;
import com.example.eventflowsaas.entity.enums.SeatStatus;
import com.example.eventflowsaas.mapper.BookingMapper;
import com.example.eventflowsaas.repository.BookingRepository;
import com.example.eventflowsaas.repository.EventRepository;
import com.example.eventflowsaas.repository.SeatRepository;
import com.example.eventflowsaas.security.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        Seat seat = seatRepository.findByIdWithLock(bookingRequestDto.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.getSeatStatus() != SeatStatus.AVAILABLE) {
            throw new RuntimeException("this is already Booking");
        }

        Event event = eventRepository.findById(bookingRequestDto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Booking booking = new Booking();
        booking.setUserId(user.getId());
        booking.setSeat(seat);
        booking.setEvent(event);
        booking.setPrice(seat.getPrice());
        booking.setCreatedAt(Instant.now());
        booking.setStatus(BookingStatus.PENDING);

        seat.setSeatStatus(SeatStatus.RESERVED);

        seat.setSeatStatus(SeatStatus.SOLD);

        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toDto(saved);
    }

    @Transactional
    public void cancelBooking(Long bookingId){
        try {
            Booking booking = bookingRepository.findById(bookingId).orElseThrow();
            Seat seat = booking.getSeat();
            if (seat != null){
                seat.setSeatStatus(SeatStatus.AVAILABLE);
            }
            bookingRepository.deleteById(bookingId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Page<BookingResponseDto> getAllBookingsForUser(Pageable pageable){
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();
        Page<Booking> bookingsByCreatedById = bookingRepository.getAllBookingsForUser(user.getId(), pageable);
        return bookingsByCreatedById.map((e)->{
            return bookingMapper.toDto(e);
        });
    }
}
