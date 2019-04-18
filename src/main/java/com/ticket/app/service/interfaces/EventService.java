package com.ticket.app.service.interfaces;


import com.ticket.app.module.Event;
import com.ticket.app.module.Ticket;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Event registEvent(Event event);

    Event getEvent(Long id);

    Event updateEvent(Event event);

    Optional<Event> getEventByTicketId(Long id);

    Optional<List<Event>> getEventByClientId(Long clientId);

    void removeEvent(Long id);

    List<Event> getAll();

    Optional<List<Ticket>> getTicketByEventId(Long eventId);
}
