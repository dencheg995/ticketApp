package com.ticket.app.service.impl;


import com.ticket.app.exeptions.event.EventExeption;
import com.ticket.app.module.Event;
import com.ticket.app.repository.EventRepository;
import com.ticket.app.repository.TicketRepository;
import com.ticket.app.service.interfaces.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final TicketRepository ticketRepository;

    private static Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    public EventServiceImpl(EventRepository eventRepository, TicketRepository ticketRepository) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Event registEvent(Event event) {
        return eventRepository.saveAndFlush(event);
    }

    @Override
    public Event getEvent(Long id) {
        if (eventRepository.getOne(id) == null) {
            logger.error("Ups, we haven't this app");
            throw new EventExeption();
        }
        return eventRepository.getOne(id);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventRepository.saveAndFlush(event);
    }


}
