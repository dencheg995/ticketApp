package com.ticket.app.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Purchase {

    @Id
    @Column(name = "purchase_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uniq_id", unique = true)
    private String uniqId;

    @ElementCollection
    @Column(name = "cost_purchase_ticket")
    private Map<Ticket, Double> costBuyTicket;

    @Column(name = "check_purchase")
    private boolean check;

    @Column(name = "num_sale")
    private Integer numSale;

    @Column(name = "purchase_time")
    private LocalDateTime localDateTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "purchase_consumer",
            joinColumns = {@JoinColumn(name = "purchase_ticket_id", foreignKey = @ForeignKey(name = "FK_PURCHASE_TICKET"))},
            inverseJoinColumns = {@JoinColumn(name = "consumer_id", foreignKey = @ForeignKey(name = "FK_CONSUMER"))})
    @JsonIgnore
    private Consumer consumer;

    @ElementCollection
//    @JoinTable(name = "purchase_ticket",
//            joinColumns = {@JoinColumn(name = "purchase_ticket_id", foreignKey = @ForeignKey(name = "FK_PURCHASE_TICKET"))},
//            inverseJoinColumns = {@JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(name = "FK_TICKET"))})
//    @JsonIgnore
//    @MapKey(name = "ticket")
    private Map<Ticket, Integer> ticket = new HashMap<>();

    public boolean isCheck() {
        return check;
    }

    public Integer getNumSale() {
        return numSale;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setNumSale(Integer numSale) {
        this.numSale = numSale;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqId() {
        return uniqId;
    }

    public void setUniqId(String uniqId) {
        this.uniqId = uniqId;
    }

    public Map<Ticket, Double> getCostBuyTicket() {
        return costBuyTicket;
    }

    public void setCostBuyTicket(Map<Ticket, Double> costBuyTicket) {
        this.costBuyTicket = costBuyTicket;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Map<Ticket, Integer> getTicket() {
        return ticket;
    }

    public void setTicket(Map<Ticket, Integer> ticket) {
        this.ticket = ticket;
    }
}
