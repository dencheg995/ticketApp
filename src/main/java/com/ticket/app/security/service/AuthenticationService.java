package com.ticket.app.security.service;

import com.ticket.app.module.Client;
import com.ticket.app.service.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

	private final ClientService userService;

	@Autowired
	public AuthenticationService(ClientService userService) {
		this.userService = userService;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Client> user = userService.getByEmailOrPhone(username, username);
		if (user == null) {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		return user.get();
	}
}
