package com.ticket.app.service.interfaces;


import com.ticket.app.module.Client;
import com.ticket.app.module.POJOUser;

public interface ClientService {

    Client addClient(Client client);

    Client getByEmailOrPhone(String email, String phone);

    Client updateClient(Client client);

    Client getClientByEventId(Long id);

    Client getClientById(Long id);

    Client edit(POJOUser client);
}
