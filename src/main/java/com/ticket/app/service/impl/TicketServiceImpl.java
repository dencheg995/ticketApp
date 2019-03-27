package com.ticket.app.service.impl;

import com.ticket.app.module.Ticket;
import com.ticket.app.repository.TicketRepository;
import com.ticket.app.service.interfaces.TicketService;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket addTicket(Ticket ticket) {
        return ticketRepository.saveAndFlush(ticket);
    }
}
