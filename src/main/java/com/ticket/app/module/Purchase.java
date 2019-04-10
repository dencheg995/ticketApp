package com.ticket.app.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "count_purchase_ticket")
    private int countBuyTicket;

    @Column(name = "cost_purchase_ticket")
    private int costBuyTicket;

    @Column(name = "check_purchase")
    private boolean check;

    @Column(name = "num_sale")
    private Integer numSale;

    @Column(name = "purchase_time")
    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinTable(name = "purchase_consumer",
            joinColumns = {@JoinColumn(name = "purchase_ticket_id", foreignKey = @ForeignKey(name = "FK_PURCHASE_TICKET"))},
            inverseJoinColumns = {@JoinColumn(name = "consumer_id", foreignKey = @ForeignKey(name = "FK_CONSUMER"))})
    @JsonIgnore
    private Consumer consumer;

    @ManyToOne
    @JoinTable(name = "purchase_ticket",
            joinColumns = {@JoinColumn(name = "purchase_ticket_id", foreignKey = @ForeignKey(name = "FK_PURCHASE_TICKET"))},
            inverseJoinColumns = {@JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(name = "FK_TICKET"))})
    @JsonIgnore
    private Ticket ticket;

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

    public int getCostBuyTicket() {
        return costBuyTicket;
    }

    public void setCostBuyTicket(int costBuyTicket) {
        this.costBuyTicket = costBuyTicket;
    }

    public int getCountBuyTicket() {
        return countBuyTicket;
    }

    public void setCountBuyTicket(int countBuyTicket) {
        this.countBuyTicket = countBuyTicket;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
