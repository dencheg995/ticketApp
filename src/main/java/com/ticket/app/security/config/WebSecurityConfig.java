package com.ticket.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	private final UserDetailsService userDetailsService;
	private final RequestMatcher csrfRequestMatcher = new RequestMatcher() {
		private final RegexRequestMatcher requestMatcher = new RegexRequestMatcher("/processing-url", null);

		@Override
		public boolean matches(HttpServletRequest request) {
			return requestMatcher.matches(request);
		}
	};


	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/signup", "/login", "/logout").permitAll();
		http
				.authorizeRequests()
				.antMatchers("/register/**").permitAll()
				.antMatchers("/lk/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers("/edit/user/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers("/stats/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers("/event/new/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers("/edit/event/**").hasAnyAuthority("ADMIN", "USER")
				.antMatchers("/remove/event/**").hasAnyAuthority("ADMIN", "USER")
//                .antMatchers("/admin/**").hasAnyAuthority("ADMIN", "OWNER")
				.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/processing-url")
				.defaultSuccessUrl("/lk")
				.failureUrl("/login?error=true")
				.usernameParameter("username")
				.passwordParameter("password").and().
				csrf().requireCsrfProtectionMatcher(csrfRequestMatcher);
		;
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
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(strength);
	}
}
