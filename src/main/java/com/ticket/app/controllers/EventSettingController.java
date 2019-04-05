package com.ticket.app.controllers;

import com.ticket.app.module.Event;
import com.ticket.app.service.interfaces.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;

@Controller
public class EventSettingController {

    private final EventService eventService;

    public EventSettingController(EventService eventService) {
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
                                           @RequestParam(value = "eventPocket") BigInteger eventPocket) {
        Event event = eventService.getEvent(eventId);
        event.setName(eventName);
        event.setAddress(eventAddress);
        event.setPocket(eventPocket);
        eventService.updateEvent(event);
        return ResponseEntity.ok(event);
    }

    @GetMapping(value = "/remove/event")
    public ResponseEntity removeEvent (@RequestParam Long eventId) {
        eventService.removeEvent(eventId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
