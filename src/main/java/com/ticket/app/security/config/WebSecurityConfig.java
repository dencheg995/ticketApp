package com.ticket.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	private final UserDetailsService userDetailsService;

	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/register/**").permitAll()
				.antMatchers("/lk/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
				.antMatchers("/edit/user/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
				.antMatchers("/stats/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
				.antMatchers("/event/new/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
				.antMatchers("/edit/event/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
				.antMatchers("/remove/event/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
//                .antMatchers("/admin/**").hasAnyAuthority("ADMIN", "OWNER")
				.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/processing-url")
				.defaultSuccessUrl("/lk")
				.failureUrl("/login?error=true")
				.usernameParameter("username")
				.passwordParameter("password");
		http.authorizeRequests().and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
		http.apply(new SpringSocialConfigurer()).signupUrl("/signup");
	}

    @Override
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

	@Value("12")
	private int strength;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(strength);
	}
}
