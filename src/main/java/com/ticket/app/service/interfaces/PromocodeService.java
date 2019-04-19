package com.ticket.app.service.interfaces;

import com.ticket.app.module.Promocode;

import java.util.Optional;


public interface PromocodeService {

    Promocode addPromo(Promocode promocode);

    Promocode editPromo(Promocode promocode);

    void remove(Long id);

    Optional<Promocode> getClientByEventId(Long ticketId);

    Optional<Promocode> getPromoById(Long promoId);

}
