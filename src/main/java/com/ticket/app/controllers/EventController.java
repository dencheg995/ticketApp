package com.ticket.app.controllers;

import com.ticket.app.service.interfaces.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/event/app/{id}")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ModelAndView eventPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("event");
        modelAndView.addObject("event", eventService.getEvent(id));
        return modelAndView;
    }

    @PostMapping
    public ResponseEntity buyTicket(@RequestParam int priceTicket) {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
