package com.ticket.app.dao;

import com.ticket.app.form.AppUserForm;
import com.ticket.app.module.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
@Transactional
public class AppUserDAO {

	@Autowired
	private EntityManager entityManager;


	@Autowired
	private AppRoleDAO appRoleDAO;

	public AppUser findAppUserByUserId(Long userId) {
		try {
			String sql = "select e from " + AppUser.class.getName() + " e where e.userId = :userId ";
			Query query = entityManager.createQuery(sql, AppUser.class);
			query.setParameter("userId", userId);
			return (AppUser) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public AppUser findAppUserByUserName(String userName) {
        try {
            String sql = "select e from " + AppUser.class.getName() + " e " 
                    + " where e.vkId = :userName or e.email = :email";
            Query query = entityManager.createQuery(sql, AppUser.class);
            query.setParameter("userName", userName);
            query.setParameter("email", userName);
            return (AppUser) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
	
	public AppUser findByEmail(String email) {
        try {
            String sql = "select e from " + AppUser.class.getName() + " e " 
                    + " where e.email = :email ";
            Query query = entityManager.createQuery(sql, AppUser.class);
            query.setParameter("email", email);
            return (AppUser) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
	
	 private String findAvailableUserName(String userName_prefix) {
	        AppUser account = this.findAppUserByUserName(userName_prefix);
	        if (account == null) {
	            return userName_prefix;
	        }
	        int i = 0;
	        while (true) {
	            String userName = userName_prefix + "_" + i++;
	            account = this.findAppUserByUserName(userName);
	            if (account == null) {
	                return userName;
	            }
	        }
	    }
	 
	// Auto create App User Account.
	    public AppUser createAppUser(Connection<?> connection) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	        ConnectionKey key = connection.getKey();
	        System.out.println("key= (" + key.getProviderId() + "," + key.getProviderUserId() + ")");
	        UserProfile userProfile = connection.fetchUserProfile();
	        String email = userProfile.getEmail();
	        AppUser appUser = this.findByEmail(email);
	        if (appUser != null) {
	            return appUser;
	        }
	        String userName_prefix = userProfile.getFirstName().trim().toLowerCase()
	                + "_" + userProfile.getLastName().trim().toLowerCase();
	  
	        String userName = this.findAvailableUserName(userName_prefix);
	        String randomPassword = UUID.randomUUID().toString().substring(0, 5);
	        String encrytedPassword = bCryptPasswordEncoder.encode(randomPassword);
	        appUser = new AppUser();
	        appUser.setEnabled(true);
	        appUser.setPassword(encrytedPassword);
	        appUser.setVkId(userName);
	        appUser.setEmail(email);
	        appUser.setFirstName(userProfile.getFirstName());
	        appUser.setLastName(userProfile.getLastName());
	        this.entityManager.persist(appUser);
	        // Create default Role
	        List<String> roleNames = new ArrayList<String>();
	        roleNames.add("USER");
	        this.appRoleDAO.createRoleFor(appUser, roleNames);
	  
	        return appUser;
	    }
	    
	    public AppUser registerNewUserAccount(AppUserForm appUserForm, List<String> roleNames) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			AppUser appUser = new AppUser();
	        appUser.setPhoneNumber(appUserForm.getTel());
	        appUser.setEmail(appUserForm.getEmail());
	        appUser.setFirstName(appUserForm.getFirstName());
	        appUser.setLastName(appUserForm.getLastName());
	        appUser.setVkId(appUserForm.getUserName());
	        appUser.setEnabled(true);
	        String encrytedPassword = bCryptPasswordEncoder.encode(appUserForm.getPassword());
	        appUser.setPassword(encrytedPassword);
	        this.entityManager.persist(appUser);
	        this.entityManager.flush();
	  
	        this.appRoleDAO.createRoleFor(appUser, roleNames);
	  
	        return appUser;
	    }
}
