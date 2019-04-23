package com.ticket.app.module;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POJOTicket {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Map<Long, Integer> ticketId = new HashMap<>();

    private Double ticketPrice;

    private int countTicket;

    private LocalDateTime date;

    private String promo;

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public Map<Long, Integer> getTicketId() {
        return ticketId;
    }

    public void setTicketId(Map<Long, Integer> ticketId) {
        this.ticketId = ticketId;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getCountTicket() {
        return countTicket;
    }

    public void setCountTicket(int countTicket) {
        this.countTicket = countTicket;
    }
}
