package com.ticket.app.repository;

import com.ticket.app.module.Event;
import com.ticket.app.module.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT purchase FROM Purchase purchase JOIN purchase.ticket ticket WHERE ticket.id = :ticketId")
    List<Purchase> getPurchaseByTicketId(@Param("ticketId") Long ticketId);

    @Query("SELECT purchase FROM Purchase purchase JOIN purchase.consumer consumer WHERE consumer.id = :consumerId")
    List<Purchase> getPurchaseByConsumerId(@Param("consumerId") Long consumerId);

    Purchase getPurchaseByUniqId(String uniqId);

    Purchase getPurchaseByNumSale(int numSale);
}
