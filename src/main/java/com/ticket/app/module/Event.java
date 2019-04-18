package com.ticket.app.module;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;
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

    @Column(name = "club_name")
    private String clubName;

    @Column(name = "event_address")
    @NotNull
    private String address;

    @Column(name = "event_pocket")
    @NotNull
    private BigInteger pocket;

    @Column(name = "event_date")
    private String date;

    private String vkPostUrl;

    private String closeVkRepost;

    private Integer saleForVkPost;

    @Column(name = "event_age_limit")
    private Integer eventAgeLimit;

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
    private AppUser client;

    public Event() {
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCloseVkRepost() {
        return closeVkRepost;
    }

    public void setCloseVkRepost(String closeVkRepost) {
        this.closeVkRepost = closeVkRepost;
    }

    public String getVkPostUrl() {
        return vkPostUrl;
    }

    public void setVkPostUrl(String vkPostUrl) {
        this.vkPostUrl = vkPostUrl;
    }



    public Integer getSaleForVkPost() {
        return saleForVkPost;
    }

    public void setSaleForVkPost(Integer saleForVkPost) {
        this.saleForVkPost = saleForVkPost;
    }

    public Integer getEventAgeLimit() {
        return eventAgeLimit;
    }

    public void setEventAgeLimit(Integer eventAgeLimit) {
        this.eventAgeLimit = eventAgeLimit;
    }

    public AppUser getClient() {
        return client;
    }

    public void setClient(AppUser client) {
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
