package com.ticket.app.controllers;

import com.ticket.app.module.Purchase;
import com.ticket.app.service.interfaces.PurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VerifyController {


    private final PurchaseService purchaseService;

    public VerifyController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/send/ticket")
    public ModelAndView vefify(@RequestParam("uniq") String uniq,
                               @RequestParam("count") int count,
                               @RequestParam("id") Long purchaseId) {
        Purchase purchase = purchaseService.getPurchaseById(purchaseId);
        ModelAndView modelAndView = new ModelAndView();
        if (purchase.getUniqId().equalsIgnoreCase(uniq) && purchase.getCountBuyTicket() == count) {
            modelAndView.setViewName("green");
        } else {
            modelAndView.setViewName("red");
        }
        return modelAndView;
    }
}
