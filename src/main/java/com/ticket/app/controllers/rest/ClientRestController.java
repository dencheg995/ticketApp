package com.ticket.app.controllers.rest;

import com.ticket.app.module.Client;
import com.ticket.app.service.interfaces.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientRestController {

    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/get/event/client")
    public ResponseEntity<Client> getEventClient(@RequestParam(value = "eventId") Long eventId) {
        System.out.println(clientService.getClientByEventId(eventId).get().getEmail());
        return ResponseEntity.ok(clientService.getClientByEventId(eventId).get());
    }
}
