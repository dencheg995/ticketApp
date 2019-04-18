package com.ticket.app.security.service;


import com.ticket.app.dao.AppRoleDAO;
import com.ticket.app.dao.AppUserDAO;
import com.ticket.app.module.AppUser;
import com.ticket.app.service.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AppUserDAO appUserDAO;

	@Autowired
	private AppRoleDAO appRoleDAO;


	private final ClientService clientService;

	public UserDetailsServiceImpl(ClientService clientService) {
		this.clientService = clientService;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		System.out.println("UserDetailsServiceImpl.loadUserByUsername=" + userName);
		AppUser appUser = clientService.getByEmailOrPhoneOrVkId(userName, userName, userName).get();
		if (appUser == null) {
			System.out.println("User not found! " + userName);
			throw new UsernameNotFoundException("User " + userName + " was not found in the database");
		}
		System.out.println("Found User: " + appUser);
		return appUser;
	}



}
