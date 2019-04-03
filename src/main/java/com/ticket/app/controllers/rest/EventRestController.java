package com.ticket.app.controllers.rest;

import com.ticket.app.module.Event;
import com.ticket.app.service.interfaces.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventRestController {

    private final EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/get/event")
    public ResponseEntity<Event> getEvent(@RequestParam Long eventId) {
        return ResponseEntity.ok( eventService.getEvent(eventId));
    }
}
