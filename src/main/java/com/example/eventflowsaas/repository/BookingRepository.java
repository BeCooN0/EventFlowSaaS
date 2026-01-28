package com.example.eventflowsaas.repository;

import com.example.eventflowsaas.entity.Booking;
import com.example.eventflowsaas.entity.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> getAllBookingsForUser(Long id, Pageable pageable);
    List<Booking> findAllByCreatedAtBeforeAndStatus(Instant created, BookingStatus status);
}
