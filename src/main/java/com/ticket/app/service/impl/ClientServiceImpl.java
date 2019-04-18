package com.ticket.app.service.impl;


import com.ticket.app.exeptions.user.UserExistsException;
import com.ticket.app.form.AppUserForm;
import com.ticket.app.module.AppUser;

import com.ticket.app.module.POJOUser;
import com.ticket.app.module.Role;
import com.ticket.app.repository.ClientRepository;
import com.ticket.app.repository.RoleRepositories;
import com.ticket.app.service.interfaces.ClientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientServiceImpl implements ClientService {

    private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    private final RoleRepositories roleRepositories;


    public ClientServiceImpl(ClientRepository clientRepository, RoleRepositories roleRepositories) {
        this.clientRepository = clientRepository;
        this.roleRepositories = roleRepositories;
    }

    @Override
    public AppUser edit(POJOUser client) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        logger.info("{}: updating of a user...", ClientServiceImpl.class.getName());
        AppUser currentClient = clientRepository.getOne(client.getId());
        currentClient.setFirstName(client.getFirstName());
        currentClient.setLastName(client.getLastName());
        currentClient.setEmail(client.getEmail());
        currentClient.setPhoneNumber(client.getPhoneNumber());
        phoneNumberValidation(currentClient);
        if (!currentClient.getPassword().equals(client.getPassword())) {
            currentClient.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
        }
        return clientRepository.saveAndFlush(currentClient);
    }

    @Override
    public AppUser getClientByEmail(String username) {
        return clientRepository.getClientByEmail(username);
    }

    @Override
    public AppUser addClient(AppUserForm appUserForm) {
        logger.info("{}: adding of a new user...", ClientServiceImpl.class.getName());
        List<Role> roleList = roleRepositories.getRoles("USER");
        AppUser appUser = new AppUser();
        appUser.setPhoneNumber(appUserForm.getTel());
        appUser.setEmail(appUserForm.getEmail());
        appUser.setFirstName(appUserForm.getFirstName());
        appUser.setLastName(appUserForm.getLastName());
        appUser.setVkId(appUserForm.getUserName());
        appUser.setEnabled(true);
        appUser.setRole(roleList);
        phoneNumberValidation(appUser);
        if (clientRepository.getClientByEmail(appUser.getEmail()) != null) {
            logger.warn("{}: user with email {} is already exist", ClientServiceImpl.class.getName(), appUser.getEmail());
            throw new UserExistsException();
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        appUser.setPassword(bCryptPasswordEncoder.encode(appUserForm.getPassword()));

        logger.info("{}: user saved successfully", ClientServiceImpl.class.getName());
        return clientRepository.save(appUser);
    }

    @Override
    public Optional<AppUser> getByEmailOrPhoneOrVkId(String email, String phone, String vkId) {
        return Optional.ofNullable(clientRepository.getByEmailOrPhoneNumberOrVkId(email, phone, vkId));
    }

    @Override
    public Optional<AppUser> getByVkId(String vkId) {
        return Optional.ofNullable(clientRepository.getByVkId(vkId));
    }

    @Override
    public AppUser updateClient(AppUser client) {
        return clientRepository.saveAndFlush(client);
    }

    @Override
    public Optional<AppUser> getClientByEventId(Long id) {
        return Optional.ofNullable(clientRepository.getClientByEventId(id));
    }

    @Override
    public Optional<AppUser> getClientById(Long id) {
        return Optional.ofNullable(clientRepository.getOne(id));
    }

    private void phoneNumberValidation(AppUser client) {
        String phoneNumber = client.getPhoneNumber();
        Pattern pattern = Pattern.compile("^((8|\\+7|7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()) {
            if (phoneNumber.startsWith("8")) {
                phoneNumber = phoneNumber.replaceFirst("8", "7");
            }
            client.setPhoneNumber(phoneNumber.replaceAll("[+()-]", "")
                    .replaceAll("\\s", ""));
        }
    }
}
