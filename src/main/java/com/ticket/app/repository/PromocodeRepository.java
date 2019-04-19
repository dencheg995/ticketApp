package com.ticket.app.repository;

import com.ticket.app.module.Promocode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PromocodeRepository extends JpaRepository<Promocode, Long> {

    @Query("SELECT promo FROM Promocode promo JOIN promo.ticket ticket WHERE ticket.id = :ticketId")
    Promocode getClientByEventId(@Param("ticketId") Long ticketId);

    @Query("SELECT promo FROM Promocode promo  WHERE promo.sale = :sale")
    Set<Promocode> getPromoByDiscount(@Param("sale") String sale);
}
