package com.ticket.app.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketId")
    private Long ticketId;

    @Column(name = "ticketType")
    private String ticketType;

    @Column(name = "ticketPrice")
    private int ticketPrice;

    @Column(name = "ticketCount")
    private int ticketCount;

    @ManyToOne
    @JoinTable(name = "event_ticket",
            joinColumns = {@JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(name = "FK_TICKET"))},
            inverseJoinColumns = {@JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "FK_EVENT"))})
    @JsonIgnore
    private Event event;

    @ManyToOne
    @JoinTable(name = "consumer_ticket",
            joinColumns = {@JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(name = "FK_TICKET"))},
            inverseJoinColumns = {@JoinColumn(name = "consumer_id", foreignKey = @ForeignKey(name = "FK_CONSUMER"))})
    @JsonIgnore
    private Consumer consumer;

    public Ticket() {
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
}
