package com.ticket.app.service.interfaces;


import com.ticket.app.module.Client;

public interface ClientService {

    Client addClient(Client client);

    Client getByEmailOrPhone(String email, String phone);

    Client updateClient(Client client);
}
