package com.ticket.app.controllers;

import com.ticket.app.module.Event;
import com.ticket.app.module.Ticket;
import com.ticket.app.service.interfaces.EventService;
import com.ticket.app.service.interfaces.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class TicketController {

    private final EventService eventService;

    private final TicketService ticketService;

    public TicketController(EventService eventService, TicketService ticketService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/tickets/new")
    public ModelAndView registerUser(@RequestParam("eventId") Long id) {
        ModelAndView modelAndView = new ModelAndView("tickets");
        modelAndView.addObject("event", eventService.getEvent(id));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/put/new/ticket")
    public ResponseEntity<Ticket> putTicket(@RequestParam Long eventId) {
        Ticket ticket = new Ticket();
        ticket.setTicketType("");
        ticket.setTicketCount(0);
        ticket.setTicketPrice(0);
        Optional<List<Ticket>> tickets = ticketService.getTicketsByEventId(eventId);
        tickets.get().add(ticket);
        Event event = eventService.getEvent(eventId);
        event.setTicketList(tickets.get());
        eventService.updateEvent(event);
        return ResponseEntity.ok(ticket);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/remove/ticket")
    public ResponseEntity removeTicket (@RequestParam Long ticketId) {
        ticketService.removeTicket(ticketId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
