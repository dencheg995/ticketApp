package com.ticket.app.exeptions.event;

public class EventException extends RuntimeException {
    public EventException() {super("Событие не найдено");}
}
