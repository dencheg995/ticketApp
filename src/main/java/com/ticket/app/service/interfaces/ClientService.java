package com.ticket.app.service.interfaces;


import com.ticket.app.form.AppUserForm;
import com.ticket.app.module.AppUser;
import com.ticket.app.module.POJOUser;

import com.ticket.app.module.Role;
import org.springframework.social.connect.Connection;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    AppUser addClient(AppUserForm user);

    Optional<AppUser> getByEmailOrPhoneOrVkId(String email, String phone, String vkId);

    Optional<AppUser> getByVkId(String vkId);

    AppUser updateClient(AppUser client);

    Optional<AppUser> getClientByEventId(Long id);

    Optional<AppUser> getClientById(Long id);

    AppUser edit(POJOUser client);

    AppUser getClientByEmail(String username);
}
