package com.ticket.app.security.service;


import com.ticket.app.dao.AppUserDAO;
import com.ticket.app.module.AppUser;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

public class ConnectionSignUpImpl implements ConnectionSignUp {

	private AppUserDAO appUserDAO;

	public ConnectionSignUpImpl(AppUserDAO appUserDAO) {
		this.appUserDAO = appUserDAO;
	}



	// After logging in social networking.
	// This method will be called to create a corresponding App_User record
	// if it does not already exist.
	@Override
	public String execute(Connection<?> connection) {
		AppUser account = appUserDAO.createAppUser(connection);
		return account.getUserName();
	}

}
