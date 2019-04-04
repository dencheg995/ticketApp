package com.ticket.app.controllers;

import com.ticket.app.module.Consumer;
import com.ticket.app.module.POJOTicket;
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

//    @PostMapping("/purchase/tickets")
//    @ResponseBody
//    public ResponseEntity buyTicket(@RequestParam(required=false, value = "firstName") String firstName,
//                                    @RequestParam(required=false, name = "lastName") String lastName,
//                                    @RequestParam(required=false, name = "email") String email,
//                                    @RequestParam(required=false, name = "phoneNumber") String phoneNumber,
//                                    @RequestParam(required=false, name = "ticketId") Long ticketId,
//                                    @RequestParam(required=false, name = "priceTicket") Integer priceTicket,
//                                    @RequestParam(required=false, name = "countTicket") Integer countTicket) {
//        Consumer consumer = new Consumer(firstName,lastName,email,phoneNumber);
//        Ticket ticket = ticketService.getTicket(ticketId);
//        Purchase purchase = new Purchase();
//        String uniqueID = UUID.randomUUID().toString();
//        purchase.setUniqId(uniqueID);
//        purchase.setCountBuyTicket(countTicket);
//        purchase.setCostBuyTicket(priceTicket);
//        purchase.setCheck(false);
//        List<Purchase> purchasesForTicket = purchaseService.getPurchaseByTicketId(ticketId);
//        purchasesForTicket.add(purchase);
//        ticket.setPurchaseTicketList(purchasesForTicket);
//        ticket.setTicketCount(ticket.getTicketCount() - countTicket);
//        ticketService.updateTicket(ticket);
//        List<Purchase> purchasesForConsumer = purchaseService.getPurchaseByConsumerId(consumer.getId());
//        purchasesForConsumer.add(purchase);
//        consumer.setPurchaseTicketList(purchasesForConsumer);
//        List<Ticket> tickets = ticketService.getTicketByConsumerId(consumer.getId());
//        tickets.add(ticket);
//        consumer.setTicketList(tickets);
//        consumerService.updateConsumer(consumer);
//        //purchaseService.sendTicket(purchase);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

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
        List<Purchase> purchasesForTicket = purchaseService.getPurchaseByTicketId(pojoTicket.getTicketId());
        purchasesForTicket.add(purchase);
        ticket.setPurchaseTicketList(purchasesForTicket);
        ticket.setTicketCount(ticket.getTicketCount() - pojoTicket.getCountTicket());
        List<Purchase> purchasesForConsumer = purchaseService.getPurchaseByConsumerId(consumer.getId());
        purchasesForConsumer.add(purchase);
        consumer.setPurchaseTicketList(purchasesForConsumer);
        List<Ticket> tickets = ticketService.getTicketByConsumerId(consumer.getId());
        tickets.add(ticket);
        consumer.setTicketList(tickets);
        consumerService.updateConsumer(consumer);
        //purchaseService.sendTicket(purchase);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
