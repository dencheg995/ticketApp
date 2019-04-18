package com.ticket.app.security.util;


import com.ticket.app.module.AppUser;
import com.ticket.app.security.service.SocialUserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.SocialUserDetails;

import java.util.List;

public class SecurityUtil {

	public static void logInUser(AppUser user) {
		SocialUserDetails userDetails = new SocialUserDetailsImpl(user);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
