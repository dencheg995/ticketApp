package com.ticket.app.repository;

import com.ticket.app.module.Promocode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocodeRepository extends JpaRepository<Promocode, Long> {
}
