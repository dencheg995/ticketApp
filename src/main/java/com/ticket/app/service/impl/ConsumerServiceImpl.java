package com.ticket.app.service.impl;

import com.ticket.app.module.Consumer;
import com.ticket.app.repository.ConsumerRepository;
import com.ticket.app.service.interfaces.ConsumerService;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;

    public ConsumerServiceImpl(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    @Override
    public Consumer updateConsumer(Consumer consumer) {
        return consumerRepository.saveAndFlush(consumer);
    }
}
