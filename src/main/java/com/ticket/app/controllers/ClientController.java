package com.ticket.app.controllers;


import com.ticket.app.module.AppUser;
import com.ticket.app.module.POJOUser;
import com.ticket.app.service.interfaces.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/edit/user")
    public ModelAndView editUser(@AuthenticationPrincipal AppUser clientSession) {
        ModelAndView modelAndView = new ModelAndView("settings");
        modelAndView.addObject("user", clientService.getClientById(clientSession.getId()));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping(value = "/edit/user/update")
    public @ResponseBody ResponseEntity edit(@RequestBody POJOUser user) {
        clientService.edit(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
