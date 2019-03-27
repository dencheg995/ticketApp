package com.ticket.app.controllers;


import com.ticket.app.module.Event;
import com.ticket.app.module.Ticket;
import com.ticket.app.service.impl.EventServiceImpl;
import com.ticket.app.service.impl.TicketServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/lk")
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
public class LKController {

    private final EventServiceImpl eventService;
    private final TicketServiceImpl ticketService;

    private static Logger logger = LoggerFactory.getLogger(LKController.class);


    @Autowired
    public LKController(EventServiceImpl eventService, TicketServiceImpl ticketService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
    }

    @RequestMapping("/add/event")
    public ResponseEntity registEvent(@Valid @RequestBody Event event){
        eventService.registEvent(event);
        logger.info("{} has register user: email {}", event.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping("/add/ticket")
    public ResponseEntity addTicket(@Valid @RequestBody Ticket ticket){
        ticketService.addTicket(ticket);
        logger.info("{} has add ticket", ticket.getTicketType());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public ModelAndView lkPage() {
        ModelAndView modelAndView = new ModelAndView("lk");
        return modelAndView;
    }
}
