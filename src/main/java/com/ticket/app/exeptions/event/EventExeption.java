package com.ticket.app.exeptions.event;

public class EventExeption extends RuntimeException {
    public EventExeption() {super("Событие не найдено");}
}
