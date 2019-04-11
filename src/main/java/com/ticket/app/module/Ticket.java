package com.ticket.app.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ticket")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketId")
    private Long id;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "purchase_ticket",
            joinColumns = {@JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(name = "FK_TICKET"))},
            inverseJoinColumns = {@JoinColumn(name = "purchase_ticket_id", foreignKey = @ForeignKey(name = "FK_PURCHASE_TICKET"))})
    private List<Purchase> purchaseTicketList;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "ticket_promo",
            joinColumns = {@JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(name = "FK_TICKET"))},
            inverseJoinColumns = {@JoinColumn(name = "promo_id", foreignKey = @ForeignKey(name = "FK_PROMO"))})
    private Set<Promocode> promocodeSet;

    public Ticket() {
    }

    public List<Purchase> getPurchaseTicketList() {
        return purchaseTicketList;
    }

    public void setPurchaseTicketList(List<Purchase> purchaseTicketList) {
        this.purchaseTicketList = purchaseTicketList;
    }

    public Set<Promocode> getPromocodeSet() {
        return promocodeSet;
    }

    public void setPromocodeSet(Set<Promocode> promocodeSet) {
        this.promocodeSet = promocodeSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
