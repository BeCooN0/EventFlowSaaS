package com.example.eventflowsaas.mapper;

import com.example.eventflowsaas.dto.EventRequestDto;
import com.example.eventflowsaas.dto.EventResponseDto;
import com.example.eventflowsaas.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper
public interface EventMapper {
    @Mapping(source = "createdBy.id", target = "id")
    Event toEvent(EventRequestDto eventRequestDto);
    @Mapping(source = "createdBy.id", target = "id")
    Event toUpdate(EventRequestDto eventRequestDto, @MappingTarget Event event);
    EventResponseDto toDto(Event event);
}
