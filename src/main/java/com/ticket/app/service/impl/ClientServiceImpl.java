package com.ticket.app.service.impl;


import com.ticket.app.exeptions.user.UserExistsException;
import com.ticket.app.module.AppUser;

import com.ticket.app.module.POJOUser;
import com.ticket.app.repository.ClientRepository;
import com.ticket.app.service.interfaces.ClientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientServiceImpl implements ClientService {

    private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;


    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public AppUser addClient(AppUser user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        logger.info("{}: adding of a new user...", ClientServiceImpl.class.getName());
        if (user.getEmail() != null) {
            phoneNumberValidation(user);
            if (clientRepository.getClientByEmail(user.getEmail()) != null) {
                logger.warn("{}: user with email {} is already exist", ClientServiceImpl.class.getName(), user.getEmail());
                throw new UserExistsException();
            }
        }
        user.setPassword(bCryptPasswordEncoder.encode((user.getPassword())));

        logger.info("{}: user saved successfully", ClientServiceImpl.class.getName());
        return clientRepository.saveAndFlush(user);
    }

    @Override
    public AppUser addClientConnection(Connection<?> connection) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        AppUser client = new AppUser();
        UserProfile userProfile = connection.fetchUserProfile();
        if (clientRepository.getClientByEmail(userProfile.getEmail()) != null) {
            logger.warn("{}: user with email {} is already exist", ClientServiceImpl.class.getName(), userProfile.getEmail());
            throw new UserExistsException();
        }

        String randomPassword = UUID.randomUUID().toString().substring(0, 5);
        String encrytedPassword = bCryptPasswordEncoder.encode(randomPassword);

        client.setFirstName(userProfile.getFirstName());
        client.setLastName(userProfile.getLastName());
        client.setEmail(userProfile.getEmail());
        client.setPassword(encrytedPassword);
        client.setVkId(userProfile.getId());
        clientRepository.saveAndFlush(client);


        return client;
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
    public Optional<AppUser> getClientByEmailOrPhoneNumberOrVkToken(String email, String phone, String vkToken) {
        return Optional.ofNullable(clientRepository.getClientByEmailOrPhoneNumberOrVkToken(email, phone, vkToken));
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
