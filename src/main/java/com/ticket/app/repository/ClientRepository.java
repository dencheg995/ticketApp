package com.ticket.app.repository;


import com.ticket.app.module.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client getClientByEmailOrPhoneNumber(String email, String phoneNumber);

    Client getClientByEmail(String email);

    @Query("SELECT client FROM Client client JOIN client.events events WHERE events.id = :eventId")
    Client getClientByEventId(@Param("eventId") Long eventId);
}
