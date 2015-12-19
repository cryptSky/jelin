package org.crama.jelin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class UserRole {
	
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	@Id
	@GeneratedValue
	@Column(name="ROLE_ID", nullable=false)
	private int roleId;
	@Column(name="ROLE", nullable=false)
	private String role;

	public UserRole() {}
	
	public UserRole(String role) {
		super();
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
		
	
}
