package com.ticket.app.controllers;

import com.ticket.app.module.Client;
import com.ticket.app.service.interfaces.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    @PostMapping(value = "/register")
    public ResponseEntity addUser(@Valid @RequestBody Client user) {
//        if (user.isEnabled()) {
//            logger.warn("CRM been attempt of hacking");
//            return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).build();
//        }
        userService.addClient(user);
        logger.info("{} has register user: email {}", user.getFirstName(), user.getEmail());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
