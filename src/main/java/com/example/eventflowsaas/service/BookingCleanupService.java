package com.example.eventflowsaas.service;

import com.example.eventflowsaas.entity.Booking;
import com.example.eventflowsaas.entity.enums.BookingStatus;
import com.example.eventflowsaas.entity.enums.SeatStatus;
import com.example.eventflowsaas.repository.BookingRepository;
import com.example.eventflowsaas.repository.SeatRepository;
import com.example.eventflowsaas.repository.TenantRepository;
import com.example.eventflowsaas.security.CurrentTenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class BookingCleanupService {
    private final BookingRepository bookingRepository;
    private final TenantRepository tenantRepository;
    private final SeatRepository seatRepository;

    public BookingCleanupService(BookingRepository bookingRepository, TenantRepository tenantRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.tenantRepository = tenantRepository;
        this.seatRepository = seatRepository;
    }

    @Scheduled(fixedDelay = 60000L, cron = "0 0 0 * * *")
    public void processTenant() {
        List<String> allIds = tenantRepository.findAllIds();
        for (String tenant : allIds) {
            try {
                CurrentTenant.setTenant(tenant);
                inactiveBookingsCleanup();
                CurrentTenant.clear();

            } catch (Exception e) {
                log.error("Failed to cleanup bookings for tenant: " + tenant, e);
            } finally {
                CurrentTenant.clear();
            }
        }
    }

    @Transactional
    public void inactiveBookingsCleanup() {
        Instant inactives = Instant.now().minus(10, ChronoUnit.MINUTES);
        List<Booking> bookings = bookingRepository.findAllByCreatedAtBeforeAndStatus(inactives, BookingStatus.PENDING);
        if (bookings.isEmpty()) return;
        for (Booking booking : bookings) {
            if (booking.getSeat() != null) {
                booking.getSeat().setSeatStatus(SeatStatus.AVAILABLE);
                seatRepository.save(booking.getSeat());
            }
            booking.setStatus(BookingStatus.EXPIRED);
            bookingRepository.save(booking);
            log.info("Booking expired: ID {}", booking.getId());
        }
    }
}
