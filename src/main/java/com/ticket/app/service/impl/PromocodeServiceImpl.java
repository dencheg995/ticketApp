package com.ticket.app.service.impl;

import com.ticket.app.module.Promocode;
import com.ticket.app.repository.PromocodeRepository;
import com.ticket.app.service.interfaces.PromocodeService;
import org.springframework.stereotype.Service;

@Service
public class PromocodeServiceImpl implements PromocodeService {

    private final PromocodeRepository promocodeRepository;

    public PromocodeServiceImpl(PromocodeRepository promocodeRepository) {
        this.promocodeRepository = promocodeRepository;
    }


    @Override
    public Promocode addPromo(Promocode promocode) {
        return promocodeRepository.saveAndFlush(promocode);
    }

    @Override
    public Promocode editPromo(Promocode promocode) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
}
