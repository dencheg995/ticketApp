package com.ticket.app.module;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "event_name")
    @NotNull
    private String name;

    @Column(name = "event_address")
    @NotNull
    private String address;

    @Column(name = "event_pocket")
    @NotNull
    private BigInteger pocket;

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getPocket() {
        return pocket;
    }

    public void setPocket(BigInteger pocket) {
        this.pocket = pocket;
    }

}
