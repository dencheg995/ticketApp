package com.ticket.app.exeptions.ticket;

public class TicketExeption extends RuntimeException {
    public TicketExeption() {
        super("Такого билета не существует");
    }
}
