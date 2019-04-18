package com.ticket.app.exeptions;

public class PassworNoEquals extends RuntimeException {
    public PassworNoEquals() {super("Пароли не совпадают");}
}
