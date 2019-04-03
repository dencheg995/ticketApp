package com.ticket.app.repository;

import com.ticket.app.module.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>{

    @Query("SELECT event FROM Event event JOIN event.ticketList ticket WHERE ticket.ticketId = :ticketId")
    Event getEventByTicketListId(@Param("ticketId") Long ticketId);

    @Query("SELECT event FROM Event event JOIN event.client client WHERE client.id = :clientId")
    List<Event> getEventByClientId(@Param("clientId") Long clientId);
}
