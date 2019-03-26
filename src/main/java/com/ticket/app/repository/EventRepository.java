package com.ticket.app.repository;

import com.ticket.app.module.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>{
    
}
