package com.ticket.app.repository;

import com.ticket.app.module.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT ticket FROM Ticket ticket JOIN ticket.event event WHERE event.id = :eventId")
    List<Ticket> getTicketsByEventId(@Param("eventId") Long eventId);

}
