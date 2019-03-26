package com.ticket.app.repository;


import com.ticket.app.module.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client getClientByEmailOrPhoneNumber(String email, String phoneNumber);

    Client getClientByEmail(String email);
}
