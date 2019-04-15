package com.ticket.app.controllers;

import com.ticket.app.module.Purchase;
import com.ticket.app.service.interfaces.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VerifyController {


    private final PurchaseService purchaseService;

    public VerifyController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/verify/ticket")
    public ModelAndView vefify(@RequestParam(required = false, name = "uniq") String uniq,
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

    @RequestMapping("/send/ticket")
    public ResponseEntity<String> sendTicket(@RequestParam int numSale) {
        Purchase purchase = purchaseService.getPurchaseByNumSale(numSale);
        purchase.getTicket().getEvent().getClient().setBalance(purchase.getTicket().getTicketPrice()*0.8);
        purchase.setCheck(true);
        purchaseService.update(purchase);
        purchaseService.sendTicket(purchase);
        return ResponseEntity.ok("redirect:/event/app");
    }


}
