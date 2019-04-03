package com.ticket.app.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "event")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "event_ticket",
            joinColumns = {@JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "FK_EVENT"))},
            inverseJoinColumns = {@JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(name = "FK_TICKET"))})
    @JsonIgnore
    private List<Ticket> ticketList;

    @ManyToOne
    @JsonIgnore
    @JoinTable(name = "client_event",
            joinColumns = {@JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "FK_EVENT"))},
            inverseJoinColumns = {@JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "FK_CLIENT"))})
    private Client client;

    public Event() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
