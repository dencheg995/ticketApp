package com.ticket.app.service.impl;


import com.ticket.app.module.Event;
import com.ticket.app.repository.EventRepository;
import com.ticket.app.service.interfaces.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event registEvent(Event event) {
        
        return eventRepository.saveAndFlush(event);
    }
}
