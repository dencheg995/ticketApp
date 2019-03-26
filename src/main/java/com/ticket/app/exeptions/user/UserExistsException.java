package com.ticket.app.exeptions.user;

public class UserExistsException extends RuntimeException {
	public UserExistsException() {
		super("Пользователь c таким e-mail уже существует");
	}
}

