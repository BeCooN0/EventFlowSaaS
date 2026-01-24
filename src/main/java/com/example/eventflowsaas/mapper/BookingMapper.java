package com.example.eventflowsaas.mapper;

import com.example.eventflowsaas.dto.BookingRequestDto;
import com.example.eventflowsaas.dto.BookingResponseDto;
import com.example.eventflowsaas.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface BookingMapper {
    BookingResponseDto toDto(Booking booking);
    Booking toBooking(BookingRequestDto bookingRequestDto);
    Booking toUpdate(BookingRequestDto bookingRequestDto, @MappingTarget Booking booking);
}
