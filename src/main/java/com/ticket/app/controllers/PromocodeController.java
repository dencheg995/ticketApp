package com.ticket.app.controllers;

import com.ticket.app.module.Promocode;
import com.ticket.app.module.Ticket;
import com.ticket.app.service.interfaces.PromocodeService;
import com.ticket.app.service.interfaces.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class PromocodeController {

    private final PromocodeService promocodeService;

    private final TicketService ticketService;

    public PromocodeController(PromocodeService promocodeService, TicketService ticketService) {
        this.promocodeService = promocodeService;
        this.ticketService = ticketService;
    }


    @RequestMapping(value = "/add-promo", method = RequestMethod.POST)
    public ResponseEntity addPromo(@RequestParam("idTicket") Long idTicket,
                                   @RequestParam("listPromo") String listPromo,
                                               @RequestParam("dateStart") String dateStart,
                                               @RequestParam("dateEnd") String dateEnd,
                                               @RequestParam("sale") String sale,
                                               @RequestParam("count") Integer count) {
        Ticket ticket = ticketService.getTicket(idTicket);
        Promocode promocode = new Promocode();
        List<String> promocodes = Arrays.asList(listPromo.split("\n"));
        promocode.setPromocode(promocodes);
        promocode.setDateStart(dateStart);
        promocode.setDateEnd(dateEnd);
        promocode.setSale(sale);
        promocode.setCount(count);
        promocode.setTicket(ticket);
        promocodeService.addPromo(promocode);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
