package com.ticket.app.service.interfaces;

import com.ticket.app.module.Ticket;

import java.util.List;

public interface TicketService {
    Ticket addTicket(Ticket ticket);

    List<Ticket> getTicketByConsumerId(Long consumerId);

    List<Ticket> getTicketsByEventId(Long eventId);

    Ticket getTicket(Long id);

    Ticket updateTicket(Ticket ticket);

    void removeTicket(Long id);

}
