package com.ticket.app.repository;

import com.ticket.app.module.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepositories extends JpaRepository<Role, Long> {

    @Query("SELECT role FROM Role role WHERE role.roleName = :roleName")
    List<Role> getRoles(@Param("roleName") String roleName);
}
