package com.ticket.app.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "consumer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Consumer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumer_id")
    private Long id;

    @Column(name = "consumer_name")
    @NotNull
    private String firstName;

    @Column(name = "consumer_last_name")
    @NotNull
    private String lastName;

    @Column(name = "consumer_email")
    @NotNull
    private String email;

    @Column(name = "consumer_phone")
    @NotNull
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "consumer_ticket",
            joinColumns = {@JoinColumn(name = "consumer_id", foreignKey = @ForeignKey(name = "FK_CONSUMER"))},
            inverseJoinColumns = {@JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(name = "FK_TICKET"))})
    private List<Ticket> ticketList;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "purchase_consumer",
            joinColumns = {@JoinColumn(name = "consumer_id", foreignKey = @ForeignKey(name = "FK_CONSUMER"))},
            inverseJoinColumns = {@JoinColumn(name = "purchase_ticket_id", foreignKey = @ForeignKey(name = "FK_PURCHASE_TICKET"))})
    private List<Purchase> purchaseTicketList;

    public Consumer() {
    }

    public List<Purchase> getPurchaseTicketList() {
        return purchaseTicketList;
    }

    public void setPurchaseTicketList(List<Purchase> purchaseTicketList) {
        this.purchaseTicketList = purchaseTicketList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
