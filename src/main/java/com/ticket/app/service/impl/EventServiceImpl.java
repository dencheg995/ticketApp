package com.ticket.app.service.impl;


import com.ticket.app.exeptions.event.EventException;
import com.ticket.app.module.Event;
import com.ticket.app.module.Ticket;
import com.ticket.app.repository.EventRepository;
import com.ticket.app.repository.TicketRepository;
import com.ticket.app.service.interfaces.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private static Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event registEvent(Event event) {
        return eventRepository.saveAndFlush(event);
    }

    @Override
    public Event getEvent(Long id) {
        if (eventRepository.getOne(id) == null) {
            logger.error("Ups, we haven't this app");
            throw new EventException();
        }
        return eventRepository.getOne(id);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventRepository.saveAndFlush(event);
    }

    @Override
    public Optional<Event> getEventByTicketId(Long id) {
        return Optional.ofNullable(eventRepository.getEventByTicketListId(id));
    }

    @Override
    public Optional<List<Event>> getEventByClientId(Long clientId) {
        return Optional.ofNullable(eventRepository.getEventByClientId(clientId));
    }

    @Override
    public void removeEvent(Long id) {
        if (eventRepository.getOne(id) != null) {
            eventRepository.delete(id);
        } else {
            throw new EventException();
        }
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<List<Ticket>> getTicketByEventId(Long eventId) {
        return Optional.ofNullable(eventRepository.getTicketByEventId(eventId));
    }

}
