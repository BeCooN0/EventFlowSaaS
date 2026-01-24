package com.example.eventflowsaas.controller;

import com.example.eventflowsaas.dto.EventRequestDto;
import com.example.eventflowsaas.dto.EventResponseDto;
import com.example.eventflowsaas.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @PostMapping("/add")
    public ResponseEntity<EventResponseDto> addEvent(@RequestBody EventRequestDto eventRequestDto){
        EventResponseDto eventResponseDto = eventService.addEvent(eventRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand(eventResponseDto.getId())
                .expand("/id")
                .toUri();
        return ResponseEntity.created(location).body(eventResponseDto);
    }

}
