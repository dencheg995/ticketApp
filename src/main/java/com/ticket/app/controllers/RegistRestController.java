package com.ticket.app.controllers;

import com.ticket.app.module.Client;
import com.ticket.app.service.interfaces.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistRestController {

    private static Logger logger = LoggerFactory.getLogger(RegistRestController.class);

    private final ClientService userService;

    public RegistRestController(ClientService userService) {
        this.userService = userService;
    }

    @Value("${spring.mail.password}")
    private String password;

    @PostMapping(value = "/register")
    public ResponseEntity addUser(@Valid @RequestBody Client user) {
        userService.addClient(user);
        logger.info("{} has register user: email {}", user.getFirstName(), user.getEmail());
        logger.info("{} is password", password);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
