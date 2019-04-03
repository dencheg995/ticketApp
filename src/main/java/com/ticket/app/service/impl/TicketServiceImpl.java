package com.ticket.app.service.impl;

import com.ticket.app.exeptions.ticket.TicketExeption;
import com.ticket.app.module.Ticket;
import com.ticket.app.repository.TicketRepository;
import com.ticket.app.service.interfaces.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Ticket> getTicketByConsumerId(Long consumerId) {
        return ticketRepository.getTicketsByConsumerId(consumerId);
    }

    @Override
    public List<Ticket> getTicketsByEventId(Long eventId) {
        return ticketRepository.getTicketsByEventId(eventId);
    }


    public Ticket getTicket(Long id) {
        return ticketRepository.getOne(id);
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        return ticketRepository.saveAndFlush(ticket);
    }

    @Override
    public void removeTicket(Long id) {
        if (ticketRepository.getOne(id) != null) {
            ticketRepository.deleteById(id);
        } else {
            throw new TicketExeption();
        }
    }


}
