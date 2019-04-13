package com.ticket.app.service.impl;


import com.ticket.app.exeptions.user.UserExistsException;
import com.ticket.app.module.Client;
import com.ticket.app.module.POJOUser;
import com.ticket.app.repository.ClientRepository;
import com.ticket.app.service.interfaces.ClientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientServiceImpl implements ClientService {

    private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addClient(Client user) {
        logger.info("{}: adding of a new user...", ClientServiceImpl.class.getName());
        phoneNumberValidation(user);
        if (clientRepository.getClientByEmail(user.getEmail()) != null) {
            logger.warn("{}: user with email {} is already exist", ClientServiceImpl.class.getName(), user.getEmail());
            throw new UserExistsException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        logger.info("{}: user saved successfully", ClientServiceImpl.class.getName());
        return clientRepository.saveAndFlush(user);
    }

    @Override
    public Client edit(POJOUser client) {
        logger.info("{}: updating of a user...", ClientServiceImpl.class.getName());
        Client currentClient = clientRepository.getOne(client.getId());
        currentClient.setFirstName(client.getFirstName());
        currentClient.setLastName(client.getLastName());
        currentClient.setEmail(client.getEmail());
        currentClient.setPhoneNumber(client.getPhoneNumber());
        phoneNumberValidation(currentClient);
        if (!currentClient.getPassword().equals(client.getPassword())) {
            currentClient.setPassword(passwordEncoder.encode(client.getPassword()));
        }
        return clientRepository.saveAndFlush(currentClient);
    }

    @Override
    public Optional<Client> getByEmailOrPhone(String email, String phone) {
        return Optional.ofNullable(clientRepository.getClientByEmailOrPhoneNumber(email, phone));
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.saveAndFlush(client);
    }

    @Override
    public Optional<Client> getClientByEventId(Long id) {
        return Optional.ofNullable(clientRepository.getClientByEventId(id));
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return Optional.ofNullable(clientRepository.getOne(id));
    }

    private void phoneNumberValidation(Client client) {
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
