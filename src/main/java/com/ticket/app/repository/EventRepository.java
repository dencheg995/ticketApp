package com.ticket.app.repository;

import com.ticket.app.module.Event;
import com.ticket.app.module.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>{

    @Query("SELECT event FROM Event event JOIN event.ticketList ticket WHERE ticket.id = :ticketId")
    Event getEventByTicketListId(@Param("ticketId") Long ticketId);

    @Query("SELECT event FROM Event event JOIN event.client client WHERE client.id = :clientId")
    List<Event> getEventByClientId(@Param("clientId") Long clientId);

    @Query("SELECT event.ticketList FROM Event event WHERE event.id = :eventId")
    List<Ticket> getTicketByEventId(@Param("eventId") Long eventId);

    @Modifying
    @Transactional
    @Query("delete from Event t where t.id = :eventId")
    void delete(@Param("eventId") Long eventId);
}
