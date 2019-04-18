package com.ticket.app.controllers;

import com.ticket.app.module.AppUser;
import com.ticket.app.module.Event;
import com.ticket.app.module.Ticket;
import com.ticket.app.service.interfaces.ClientService;
import com.ticket.app.service.interfaces.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigInteger;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
public class EventSettingController {


    private final ClientService clientService;
    private final EventService eventService;

    private static Logger logger = LoggerFactory.getLogger(EventSettingController.class);

    public EventSettingController(ClientService clientService, EventService eventService) {
        this.clientService = clientService;
        this.eventService = eventService;
    }

    @GetMapping(value = "/event/new")
    public ModelAndView newEvent(@RequestParam("eventId") Long id) {
        ModelAndView modelAndView = new ModelAndView("event-settings");
        modelAndView.addObject("event", eventService.getEvent(id));
        return modelAndView;
    }

    @GetMapping(value = "/edit/event")
    public ResponseEntity<Event> editEvent(@RequestParam(value = "eventId") Long eventId,
                                           @RequestParam(value = "eventName") String eventName,
                                           @RequestParam(value = "eventAddress") String eventAddress,
                                           @RequestParam(value = "eventPocket") BigInteger eventPocket,
                                           @RequestParam(value = "eventDate") String date,
                                           @RequestParam(value = "eventAgeLimit") Integer eventAgeLimit,
                                           @RequestParam(value = "eventClubName") String clubName,
                                           @RequestParam(value = "eventSaleForVkPost") Integer saleForVkPost,
                                           @RequestParam(value = "eventCloseVkRepost") String eventCloseVkRepost,
                                           @RequestParam(value = "eventVkPostUrl") String eventVkPostUrl) {
        Event event = eventService.getEvent(eventId);
        event.setName(eventName);
        event.setAddress(eventAddress);
        event.setPocket(eventPocket);
        event.setDate(date);
        event.setEventAgeLimit(eventAgeLimit);
        event.setClubName(clubName);
        event.setSaleForVkPost(saleForVkPost);
        event.setCloseVkRepost(eventCloseVkRepost);
        event.setVkPostUrl(eventVkPostUrl);
        eventService.updateEvent(event);
        return ResponseEntity.ok(event);
    }


    @GetMapping(value = "/remove/event")
    public ResponseEntity removeEvent(@RequestParam Long eventId) {
        eventService.removeEvent(eventId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/event/create")
    public ModelAndView addEvent() {
        ModelAndView modelAndView = new ModelAndView("addEvent");
        return modelAndView;
    }

    @RequestMapping("/add/event")
    public ResponseEntity registEvent(@Valid @RequestBody Event event,
                                      @AuthenticationPrincipal AppUser clientSession){
        Optional<List<Event>> events = eventService.getEventByClientId(clientSession.getId());
        events.get().add(event);
        clientSession.setEvents(events.get());
        clientService.updateClient(clientSession);
        logger.info("{} has register event ", event.getName());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
