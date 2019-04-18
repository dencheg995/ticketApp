package com.ticket.app.security.service;


import com.ticket.app.module.AppUser;
import com.ticket.app.module.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SocialUserDetailsImpl implements SocialUserDetails{

	private static final long serialVersionUID = 1L;
	private List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
	private AppUser appUser;

	public SocialUserDetailsImpl(AppUser appUser) {
		this.appUser = appUser;

		for (Role roleName : appUser.getRole()) {
			GrantedAuthority grant = new SimpleGrantedAuthority(roleName.getRoleName());
			this.list.add(grant);
		}
	}

	@Override
	public String getUserId() {
		return this.appUser.getId() + "";
	}

	@Override
	public String getUsername() {
		return appUser.getVkId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return list;
	}

	@Override
	public String getPassword() {
		return appUser.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}