package com.ticket.app.repository;

import com.ticket.app.module.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

}
