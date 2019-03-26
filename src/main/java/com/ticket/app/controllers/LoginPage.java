package com.ticket.app.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPage {

    @GetMapping(value = {"/", "/login"})
    public String homePage() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return "login";
        } else {
            return "redirect:/lk";
        }
    }

    @GetMapping(value = {"/accessDenied"})
    public String accessDenied() {
        return "login";
    }
}
