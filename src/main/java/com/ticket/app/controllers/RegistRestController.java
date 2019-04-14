package com.ticket.app.controllers;

import com.ticket.app.module.Client;
import com.ticket.app.module.Role;
import com.ticket.app.repository.RoleRepository;
import com.ticket.app.service.interfaces.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistRestController {

    private static Logger logger = LoggerFactory.getLogger(RegistRestController.class);

    private final ClientService userService;

    private  final RoleRepository roleRepository;

    public RegistRestController(ClientService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostMapping(value = "/register")
    public ResponseEntity addUser(@Valid @RequestBody Client user) {
        userService.addClient(user);
        logger.info("{} has register user: email {}", user.getFirstName(), user.getEmail());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/add/two/role/for/app")
    public ResponseEntity addRole() {
            Role roleAdmin = new Role();
            roleAdmin.setRoleName("ADMIN");
            Role roleUser = new Role();
            roleUser.setRoleName("USER");
            roleRepository.saveAndFlush(roleAdmin);
            roleRepository.saveAndFlush(roleUser);
            return ResponseEntity.ok(HttpStatus.OK);
    }
}
