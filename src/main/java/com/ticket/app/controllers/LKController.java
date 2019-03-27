package com.ticket.app.controllers;


import com.ticket.app.module.Client;
import com.ticket.app.module.Event;
import com.ticket.app.module.Ticket;
import com.ticket.app.service.interfaces.ClientService;
import com.ticket.app.service.interfaces.EventService;
import com.ticket.app.service.interfaces.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/lk")
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
public class LKController {

    private final EventService eventService;
    private final TicketService ticketService;

    private final ClientService clientService;

    private static Logger logger = LoggerFactory.getLogger(LKController.class);


    @Autowired
    public LKController(EventService eventService, TicketService ticketService, ClientService clientService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.clientService = clientService;
    }

    @RequestMapping("/add/event")
    public ResponseEntity registEvent(@Valid @RequestBody Event event,
                                      @AuthenticationPrincipal Client clientSession){
        List<Event> events = new ArrayList<>();
        events.add(event);
        clientSession.setEvents(events);
        clientService.updateClient(clientSession);
        logger.info("{} has register event: email {}", event.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping("/add/ticket")
    public ResponseEntity addTicket(@Valid @RequestBody List<Ticket> tickets,
                                    @RequestParam Long eventId){
        Event event = eventService.getEvent(eventId);
        event.setTicketList(tickets);
        eventService.updateEvent(event);
        logger.info("{} has add ticket to ", event.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public ModelAndView lkPage(@AuthenticationPrincipal Client clientSession) {
        ModelAndView modelAndView = new ModelAndView("lk");
        modelAndView.addObject("clientEvents", clientSession);
        return modelAndView;
    }
}
