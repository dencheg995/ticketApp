package com.ticket.app.security.config;


import com.ticket.app.dao.AppUserDAO;
import com.ticket.app.security.service.ConnectionSignUpImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;

import org.springframework.social.security.AuthenticationNameUserIdSource;

import org.springframework.social.vkontakte.connect.VKontakteConnectionFactory;

import javax.sql.DataSource;

@Configuration
@EnableSocial
@PropertySource(value = "file:./social-cfg.properties")
public class SocialConfig implements SocialConfigurer {

	@Autowired
    private DataSource dataSource;
	@Autowired
    private AppUserDAO appUserDAO;
	
	private boolean autoSignUp = false;  
    
	@Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        try {
            this.autoSignUp = Boolean.parseBoolean(env.getRequiredProperty("social.auto-signup"));
        } catch (Exception e) {
            this.autoSignUp = false;
        }

        VKontakteConnectionFactory vKontakteConnectionFactory = new VKontakteConnectionFactory(
                env.getRequiredProperty("vk.app.clientId"),
                env.getRequiredProperty("vk.app.secret"));
        vKontakteConnectionFactory.setScope(env.getRequiredProperty("vk.scope"));

        cfConfig.addConnectionFactory(vKontakteConnectionFactory);

 
 
	}
	
	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}
	
	@Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource,connectionFactoryLocator, Encryptors.noOpText());
  
        if (autoSignUp) {
            ConnectionSignUp connectionSignUp = new ConnectionSignUpImpl(appUserDAO);
            usersConnectionRepository.setConnectionSignUp(connectionSignUp);
        } else {
            usersConnectionRepository.setConnectionSignUp(null);
        }
        return usersConnectionRepository;
    }
	
    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,  ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }
	
}
