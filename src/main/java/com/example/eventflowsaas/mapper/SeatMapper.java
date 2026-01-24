package com.example.eventflowsaas.mapper;

import com.example.eventflowsaas.dto.SeatRequestDto;
import com.example.eventflowsaas.dto.SeatResponseDto;
import com.example.eventflowsaas.entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    SeatResponseDto toDto(Seat seat);
    Seat toSeat(SeatRequestDto seatRequestDto);
    Seat toUpdate(SeatRequestDto seatRequestDto, @MappingTarget Seat seat);
    List<SeatResponseDto> toDtoList(List<Seat> seats);
}
