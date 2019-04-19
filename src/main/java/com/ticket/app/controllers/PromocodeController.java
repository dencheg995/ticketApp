package com.ticket.app.controllers;

import com.ticket.app.module.PromoPOJO;
import com.ticket.app.module.Promocode;
import com.ticket.app.module.Ticket;
import com.ticket.app.service.interfaces.EventService;
import com.ticket.app.service.interfaces.PromocodeService;
import com.ticket.app.service.interfaces.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class PromocodeController {

    private final PromocodeService promocodeService;

    private final TicketService ticketService;
    private final EventService eventService;

    public PromocodeController(PromocodeService promocodeService, TicketService ticketService, EventService eventService) {
        this.promocodeService = promocodeService;
        this.ticketService = ticketService;
        this.eventService = eventService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/promo/new")
    public ModelAndView newPromo(@RequestParam Long ticketId,
                                 @RequestParam Long eventId) {
        ModelAndView modelAndView = new ModelAndView("addPromocodes");
        modelAndView.addObject("ticket",ticketService.getTicket(ticketId));
        modelAndView.addObject("event",eventService.getEvent(eventId));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value = "/add-promo", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity addPromo(@RequestBody PromoPOJO promoPOJO) {
        Ticket ticket = ticketService.getTicket(promoPOJO.getIdTicket());
        Promocode promocode = new Promocode();
        List<String> promocodes = Arrays.asList(promoPOJO.getPromocodes().split("\n"));
        promocode.setPromocode(promocodes);
        promocode.setDateStart(promoPOJO.getPromoStartDate());
        promocode.setDateEnd(promoPOJO.getPromoEndDate());
        promocode.setSale(promoPOJO.getDiscountValue());
        promocode.setCount(promoPOJO.getCount());
        promocode.setTicket(ticket);
        promocodeService.addPromo(promocode);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/promo/edit")
    public ModelAndView editPromo(@RequestParam Long ticketId,
                                  @RequestParam Long eventId) {
        ModelAndView modelAndView = new ModelAndView("listPromo");
        modelAndView.addObject("ticketEdit",ticketService.getTicket(ticketId).getPromocodeSet());
        modelAndView.addObject("ticket",ticketService.getTicket(ticketId));
        modelAndView.addObject("event",eventService.getEvent(eventId));
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/get-promo")
    public ResponseEntity<Promocode> getPromo(@RequestParam Long promoId) {
        return promocodeService.getPromoById(promoId).map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
