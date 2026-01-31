package com.example.eventflowsaas.service;

import com.example.eventflowsaas.entity.Booking;
import com.example.eventflowsaas.entity.Seat;
import com.example.eventflowsaas.entity.enums.BookingStatus;
import com.example.eventflowsaas.entity.enums.SeatStatus;
import com.example.eventflowsaas.repository.BookingRepository;
import com.example.eventflowsaas.repository.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingCleanupServiceTest {
    private Booking booking;
    private Seat seat;
    @Mock
    private BookingRepository bookingRepository;
    private List<String> tenants;
    @InjectMocks
    private BookingCleanupService bookingCleanupService;
    private TenantRepository tenantRepository;
    @BeforeEach
    void signUp(){
        seat.setSeatStatus(SeatStatus.RESERVED);
        seat.setSeatNumber("ajkcr");
        booking.setStatus(BookingStatus.PENDING);
        booking.setSeat(seat);
        booking.setQuantity(1L);
        booking.setId(1L);

        when(tenantRepository.findAllIds()).thenReturn(tenants);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
    }

    @Test
    public void processTenant_ShouldCleanup_Every_Tenant(){
        bookingCleanupService.inactiveBookingsCleanup();
        verify(bookingRepository, times(2)).findAllByCreatedAtBeforeAndStatus(any(), BookingStatus.PENDING);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }
}