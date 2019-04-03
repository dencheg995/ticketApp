package com.ticket.app.controllers;

import com.ticket.app.module.Client;
import com.ticket.app.service.interfaces.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/register")
    public ModelAndView registerUser() {
        ModelAndView modelAndView = new ModelAndView("user-registration");
        return modelAndView;
    }
}
