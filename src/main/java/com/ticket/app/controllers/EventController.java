package com.ticket.app.controllers;

import com.ticket.app.service.interfaces.EventService;
import com.ticket.app.service.interfaces.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/event/app/{id}")
public class EventController {

    private final EventService eventService;

    private final TicketService ticketService;

    public EventController(EventService eventService, TicketService ticketService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public ModelAndView eventPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("event");
        modelAndView.addObject("event", eventService.getEvent(id));
        return modelAndView;
    }

    @PostMapping
    public ResponseEntity buyTicket(@RequestParam int priceTicket,
                                    @RequestParam int countTicket) {
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
