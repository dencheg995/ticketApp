package com.ticket.app.service.impl;

import com.ticket.app.module.Promocode;
import com.ticket.app.repository.PromocodeRepository;
import com.ticket.app.service.interfaces.PromocodeService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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

    @Override
    public Optional<Promocode> getClientByEventId(Long ticketId) {
        return Optional.ofNullable(promocodeRepository.getClientByEventId(ticketId));
    }

    @Override
    public Optional<Promocode> getPromoById(Long promoId) {
        return Optional.ofNullable(promocodeRepository.getOne(promoId));
    }

    @Override
    public Set<Promocode> getPromoByDiscount(String sale) {
        return promocodeRepository.getPromoByDiscount(sale);
    }


}
