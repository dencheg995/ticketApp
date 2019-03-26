package com.ticket.app.controllers;


import com.ticket.app.module.Event;
import com.ticket.app.service.impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/lk")
public class LKController {
    private final EventServiceImpl eventService;

    @Autowired
    public LKController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @RequestMapping("/add-event")
    public Event registEvent(@RequestParam("name") String name,
                             @RequestParam("address") String address,
                             @RequestParam("pocket") String pocket,
                             @RequestParam("nameTicket")String nameTicket,
                             @RequestParam("priceTicket") int priceTicket){
        Event event = new Event();
        event.setName(name);
        event.setAddress(address);
        event.setPocket(pocket);
        event.setNameTicket(nameTicket);

        event.setPriceTicket(priceTicket);
        return eventService.registEvent(event);
    }

    @GetMapping
    public ModelAndView lkPage() {
        ModelAndView modelAndView = new ModelAndView("lk");
        return modelAndView;
    }
}
