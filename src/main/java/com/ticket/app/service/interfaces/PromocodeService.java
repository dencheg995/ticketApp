package com.ticket.app.service.interfaces;

import com.ticket.app.module.Promocode;

public interface PromocodeService {

    Promocode addPromo(Promocode promocode);

    Promocode editPromo(Promocode promocode);

    void remove(Long id);

}
