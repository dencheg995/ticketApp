package com.ticket.app.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Роль (user, admin и тд)
 */
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "role_name", unique = true, nullable = false)
	private String roleName;

	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinTable(name = "permissions",
			joinColumns = {@JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_ROLE"))},
			inverseJoinColumns = {@JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "FK_CLIENT"))})
	private List<AppUser> clients = new ArrayList<>();

	public Role() {
	}

	public Role(String roleName) {
		this.roleName = roleName;
	}

	public List<AppUser> getClients() {
		return clients;
	}

	public void setClients(List<AppUser> clients) {
		this.clients = clients;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String getAuthority() {
		return roleName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Role role = (Role) o;

		return roleName != null ? roleName.equals(role.roleName) : role.roleName == null;
	}

	@Override
	public int hashCode() {
		return roleName != null ? roleName.hashCode() : 0;
	}
}