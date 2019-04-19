package com.ticket.app.module;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "promo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Promocode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promo_id")
    private Long id;


    @Column(name = "promocode")
    @ElementCollection
    private List<String> promocode;

    @Column(name = "date_start")
    private String dateStart;

    @Column(name = "date_end")
    private String dateEnd;

    @Column(name = "sale")
    private String sale;

    private Integer count;

    @ManyToOne
    @JoinTable(name = "ticket_promo",
            joinColumns = {@JoinColumn(name = "promo_id", foreignKey = @ForeignKey(name = "FK_PROMO"))},
            inverseJoinColumns = {@JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(name = "FK_TICKET"))})
    private Ticket ticket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getPromocode() {
        return promocode;
    }

    public void setPromocode(List<String> promocode) {
        this.promocode = promocode;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
