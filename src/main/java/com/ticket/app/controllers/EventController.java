package com.ticket.app.controllers;

import com.ticket.app.module.*;
import com.ticket.app.service.interfaces.ConsumerService;
import com.ticket.app.service.interfaces.EventService;
import com.ticket.app.service.interfaces.PurchaseService;
import com.ticket.app.service.interfaces.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/event/app/{id}")
public class EventController {

    private final EventService eventService;

    private final TicketService ticketService;

    private final PurchaseService purchaseService;

    private final ConsumerService consumerService;

    public EventController(EventService eventService, TicketService ticketService, PurchaseService purchaseService, ConsumerService consumerService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.purchaseService = purchaseService;
        this.consumerService = consumerService;
    }

    @GetMapping
    public ModelAndView eventPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("sale");
        modelAndView.addObject("event", eventService.getEvent(id));
        return modelAndView;
    }


    @RequestMapping(value = "/purchase/tickets")
    public @ResponseBody ResponseEntity buyTicket(@RequestBody POJOTicket pojoTicket) {
        Consumer consumer = new Consumer(pojoTicket.getFirstName(), pojoTicket.getLastName(), pojoTicket.getEmail(), pojoTicket.getPhoneNumber());
        Ticket ticket = ticketService.getTicket(pojoTicket.getTicketId());
        Purchase purchase = new Purchase();
        String uniqueID = UUID.randomUUID().toString();
        purchase.setUniqId(uniqueID);
        purchase.setCountBuyTicket(pojoTicket.getCountTicket());
        purchase.setCostBuyTicket(pojoTicket.getTicketPrice());
        purchase.setCheck(false);
        purchase.setNumSale(ticket.getTicketCount());
        purchase.setLocalDateTime(pojoTicket.getDate());
        List<Purchase> purchasesForTicket = purchaseService.getPurchaseByTicketId(pojoTicket.getTicketId());
        purchasesForTicket.add(purchase);
        ticket.setPurchaseTicketList(purchasesForTicket);
        ticket.setTicketCount(ticket.getTicketCount() - pojoTicket.getCountTicket());
        List<Purchase> purchasesForConsumer = purchaseService.getPurchaseByConsumerId(consumer.getId());
        purchasesForConsumer.add(purchase);
        consumer.setPurchaseTicketList(purchasesForConsumer);
        consumerService.updateConsumer(consumer);
        ticketService.updateTicket(ticket);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
