package com.ticket.app.service.interfaces;


import com.ticket.app.module.Event;

public interface EventService {
    Event registEvent(Event event);

    Event getEvent(Long id);

    Event updateEvent(Event event);

    Event getEventByTicketId(Long id);
}
