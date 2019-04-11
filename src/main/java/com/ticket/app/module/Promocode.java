package com.ticket.app.module;


import javax.persistence.*;

@Entity
@Table(name = "promo")
public class Promocode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promo_id")
    private Long id;


    @Column(name = "promocode")
    private String promocode;


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

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
