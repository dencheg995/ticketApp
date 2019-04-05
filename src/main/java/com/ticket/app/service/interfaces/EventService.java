package com.ticket.app.service.interfaces;


import com.ticket.app.module.Event;

import java.util.List;

public interface EventService {
    Event registEvent(Event event);

    Event getEvent(Long id);

    Event updateEvent(Event event);

    Event getEventByTicketId(Long id);

    List<Event> getEventByClientId(Long clientId);

    void removeEvent(Long id);
}
