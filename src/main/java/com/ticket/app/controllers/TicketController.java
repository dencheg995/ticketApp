package com.ticket.app.controllers;

import com.ticket.app.module.Event;
import com.ticket.app.module.Ticket;
import com.ticket.app.service.interfaces.EventService;
import com.ticket.app.service.interfaces.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TicketController {

    private final EventService eventService;

    private final TicketService ticketService;

    public TicketController(EventService eventService, TicketService ticketService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
    }

    @GetMapping(value = "/tickets/new")
    public ModelAndView registerUser(@RequestParam("eventId") Long id) {
        ModelAndView modelAndView = new ModelAndView("tickets");
        modelAndView.addObject("event", eventService.getEvent(id));
        return modelAndView;
    }

    @GetMapping(value = "/put/new/ticket")
    public ResponseEntity<Ticket> putTicket(@RequestParam Long eventId) {
        Ticket ticket = new Ticket();
        ticket.setTicketType("");
        ticket.setTicketCount(0);
        ticket.setTicketPrice(0);
        List<Ticket> tickets = ticketService.getTicketsByEventId(eventId);
        tickets.add(ticket);
        Event event = eventService.getEvent(eventId);
        event.setTicketList(tickets);
        eventService.updateEvent(event);
        return ResponseEntity.ok(ticket);
    }


    @GetMapping(value = "/edit/ticket")
    public ResponseEntity<Ticket> editTicket(@RequestParam(value = "ticketId") Long ticketId,
                                             @RequestParam(value = "ticketType") String ticketType,
                                             @RequestParam(value = "ticketCount") int ticketCount,
                                             @RequestParam(value = "ticketPrice") int ticketPrice) {
        Ticket ticket = ticketService.getTicket(ticketId);
        ticket.setTicketType(ticketType);
        ticket.setTicketCount(ticketCount);
        ticket.setTicketPrice(ticketPrice);
        ticketService.updateTicket(ticket);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping(value = "/remove/ticket")
    public ResponseEntity removeTicket (@RequestParam Long ticketId) {
        ticketService.removeTicket(ticketId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
