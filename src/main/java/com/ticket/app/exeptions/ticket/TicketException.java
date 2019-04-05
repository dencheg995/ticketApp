package com.ticket.app.exeptions.ticket;

public class TicketException extends RuntimeException {
    public TicketException() {
        super("Такого билета не существует");
    }
}
