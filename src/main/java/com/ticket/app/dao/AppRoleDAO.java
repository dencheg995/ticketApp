package com.ticket.app.dao;


import com.ticket.app.module.AppUser;
import com.ticket.app.module.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AppRoleDAO {

	@Autowired
    private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<String> getRoleNames(Long userId) {
        String sql = "Select ur.roleName from " + Role.class.getName() + " ur "
                + "join ur.appUser a where a.id = :id ";
        Query query = this.entityManager.createQuery(sql, String.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }
	
	 public Role findAppRoleByName(String roleName) {
	        try {
	            String sql = "Select e from " + Role.class.getName() + " e "
	                    + " where e.roleName = :roleName ";
	 
	            Query query = this.entityManager.createQuery(sql, Role.class);
	            query.setParameter("roleName", roleName);
	            return (Role) query.getSingleResult();
	        } catch (NoResultException e) {
	            return null;
	        }
	    }
	
	 public void createRoleFor(AppUser appUser, List<String> roleNames) {
	        //
	        for (String roleName : roleNames) {
	            Role role = this.findAppRoleByName(roleName);
	            if (role == null) {
	                role = new Role();
	                role.setRoleName("USER");
	                this.entityManager.persist(role);
	                this.entityManager.flush();
	            }
//	            UserRole userRole = new UserRole();
//	            userRole.setAppRole(role);
//	            userRole.setAppUser(appUser);
//	            this.entityManager.persist(userRole);
//	            this.entityManager.flush();
	        }
	    }
	 
	 
	 
	
}
