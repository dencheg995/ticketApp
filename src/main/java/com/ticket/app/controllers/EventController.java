package com.ticket.app.controllers;

import com.ticket.app.module.*;
import com.ticket.app.service.interfaces.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/event/app/{id}")
public class EventController {

    private final EventService eventService;

    private final TicketService ticketService;

    private final PurchaseService purchaseService;

    private final ConsumerService consumerService;

    private final PromocodeService promocodeService;

    public EventController(EventService eventService, TicketService ticketService, PurchaseService purchaseService, ConsumerService consumerService, PromocodeService promocodeService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.purchaseService = purchaseService;
        this.consumerService = consumerService;
        this.promocodeService = promocodeService;
    }

    @GetMapping
    public ModelAndView eventPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("sale");
        modelAndView.addObject("event", eventService.getEvent(id));
        return modelAndView;
    }


    @RequestMapping(value = "/purchase/tickets")
    public @ResponseBody ResponseEntity buyTicket(@RequestBody POJOTicket pojoTicket) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm МСК");
        Map<Ticket,Integer> tickets = new HashMap<>();
        Map<Ticket,Double> costs = new HashMap<>();
        Ticket ticket;
        Promocode promocode;
        Consumer consumer = new Consumer(pojoTicket.getFirstName(), pojoTicket.getLastName(), pojoTicket.getEmail(), pojoTicket.getPhoneNumber());
        Purchase purchase = new Purchase();
        String uniqueID = UUID.randomUUID().toString();
        purchase.setUniqId(uniqueID);
        purchase.setCheck(false);
        purchase.setLocalDateTime(pojoTicket.getDate());
        purchase.setConsumer(consumer);
        for (Map.Entry<Long, Integer> entry : pojoTicket.getTicketId().entrySet()) {
            ticket = ticketService.getTicket(entry.getKey());
            promocode = promocodeService.getPromoByTicketId(entry.getKey()).get();
            LocalDateTime destinationDate = LocalDateTime.parse(promocode.getDateEnd(), dateTimeFormatter);

            if (promocode.getPromocode().contains(pojoTicket.getPromo()) & !pojoTicket.getDate().equals(destinationDate)) {
                costs.put(ticket, ((pojoTicket.getTicketPrice() * (1 - (Double.parseDouble(promocode.getSale())/100.0))) * 1.1d));
                purchase.setCostBuyTicket(costs);
            } else {
                costs.put(ticket, (pojoTicket.getTicketPrice() * 1.1d));
                purchase.setCostBuyTicket(costs);
            }
            tickets.put(ticket, entry.getValue());
            ticket.setTicketCount(ticket.getTicketCount() - entry.getValue());
            ticketService.updateTicket(ticket);

        }
        purchase.setTicket(tickets);
        purchase.setConsumer(consumer);
        purchaseService.update(purchase);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
