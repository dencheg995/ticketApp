package com.ticket.app.repository;

import com.ticket.app.module.Event;
import com.ticket.app.module.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.*;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

//    @Query("SELECT tickect FROM Ticket ticket WHERE ticket.event = :event")
//    List<Ticket> getTicketsByEventId(@Param("event") Event event);
}
