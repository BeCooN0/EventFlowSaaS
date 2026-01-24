package com.example.eventflowsaas.service;

import com.example.eventflowsaas.dto.EventRequestDto;
import com.example.eventflowsaas.dto.EventResponseDto;
import com.example.eventflowsaas.entity.Event;
import com.example.eventflowsaas.entity.User;
import com.example.eventflowsaas.mapper.EventMapper;
import com.example.eventflowsaas.repository.EventRepository;
import com.example.eventflowsaas.repository.SeatRepository;
import com.example.eventflowsaas.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    public EventService(EventMapper eventMapper, EventRepository eventRepository, SeatRepository seatRepository) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public EventResponseDto addEvent(EventRequestDto eventRequestDto){
        Event event = eventMapper.toEvent(eventRequestDto);
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();
        event.setCreatedBy(user);
        Event saved = eventRepository.save(event);
        return eventMapper.toDto(saved);
    }

}

