package com.ticket.app.controllers;

import com.ticket.app.module.Consumer;
import com.ticket.app.module.Purchase;
import com.ticket.app.module.Ticket;
import com.ticket.app.service.interfaces.ConsumerService;
import com.ticket.app.service.interfaces.EventService;
import com.ticket.app.service.interfaces.PurchaseService;
import com.ticket.app.service.interfaces.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
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
        ModelAndView modelAndView = new ModelAndView("event");
        modelAndView.addObject("event", eventService.getEvent(id));
        return modelAndView;
    }

    @PostMapping
    public ResponseEntity buyTicket(@Valid @RequestBody Consumer consumer,
                                    @RequestParam Long ticketId,
                                    @RequestParam int priceTicket,
                                    @RequestParam int countTicket) {
        Ticket ticket = ticketService.getTicket(ticketId);
        Purchase purchase = new Purchase();
        String uniqueID = UUID.randomUUID().toString();
        purchase.setUniqId(uniqueID);
        purchase.setCountBuyTicket(countTicket);
        purchase.setCostBuyTicket(priceTicket);
        List<Purchase> purchasesForTicket = purchaseService.getPurchaseByTicketId(ticketId);
        purchasesForTicket.add(purchase);
        ticket.setPurchaseTicketList(purchasesForTicket);
        ticket.setTicketCount(ticket.getTicketCount() - countTicket);
        ticketService.updateTicket(ticket);
        List<Purchase> purchasesForConsumer = purchaseService.getPurchaseByConsumerId(consumer.getId());
        purchasesForConsumer.add(purchase);
        consumer.setPurchaseTicketList(purchasesForConsumer);
        List<Ticket> tickets = ticketService.getTicketByConsumerId(consumer.getId());
        tickets.add(ticket);
        consumer.setTicketList(tickets);
        consumerService.updateConsumer(consumer);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
