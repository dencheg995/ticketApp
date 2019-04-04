package com.ticket.app.service.interfaces;

import com.ticket.app.module.Purchase;

import java.util.List;

public interface PurchaseService {

    List<Purchase> getPurchaseByTicketId(Long ticketId);

    List<Purchase> getPurchaseByConsumerId(Long consumerId);

    Purchase getPurchase(String uniqId);

    void sendTicket(Purchase purchase);

    Purchase getPurchaseById(Long id);

    Purchase getPurchaseByNumSale(int numSale);

    Purchase update(Purchase purchase);

}
