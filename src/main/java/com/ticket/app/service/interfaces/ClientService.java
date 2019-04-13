package com.ticket.app.service.interfaces;


import com.ticket.app.module.Client;
import com.ticket.app.module.POJOUser;

import java.util.Optional;

public interface ClientService {

    Client addClient(Client client);

    Optional<Client> getByEmailOrPhone(String email, String phone);

    Client updateClient(Client client);

    Optional<Client> getClientByEventId(Long id);

    Optional<Client> getClientById(Long id);

    Client edit(POJOUser client);
}
