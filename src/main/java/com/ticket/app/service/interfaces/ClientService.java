package com.ticket.app.service.interfaces;


import com.ticket.app.module.AppUser;
import com.ticket.app.module.POJOUser;

import org.springframework.social.connect.Connection;

import java.util.Optional;

public interface ClientService {

    AppUser addClient(AppUser client);

    AppUser addClientConnection(Connection<?> connection);

    Optional<AppUser> getClientByEmailOrPhoneNumberOrVkToken(String email, String phone, String vkToken);

   AppUser updateClient(AppUser client);

    Optional<AppUser> getClientByEventId(Long id);

    Optional<AppUser> getClientById(Long id);

    AppUser edit(POJOUser client);

    AppUser getClientByEmail(String username);
}
