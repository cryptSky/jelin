package org.crama.jelin.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class UserRole implements Serializable {
	
	private static final long serialVersionUID = -6558128018092674791L;
	
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	@Id
	@Column(name="ROLE_ID", nullable=false)
	private int roleId;
	@Column(name="ROLE", nullable=false)
	private String role;
	
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy = "role")
	private Set<UserModel> userSet = new HashSet<UserModel>();
	
/*	@ManyToMany(mappedBy = "roles")
	private Set<UserModel> userModelSet = new HashSet<UserModel>();*/

	
	public UserRole() {}
	
	public UserRole(String role) {
		super();
		this.role = role;
	}

	/*public Set<UserModel> getUserModelSet() {
		return userModelSet;
	}

	public void setUserModelSet(Set<UserModel> userModelSet) {
		this.userModelSet = userModelSet;
	}*/
	
	
	public String getRole() {
		return role;
	}

	public Set<UserModel> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<UserModel> userSet) {
		this.userSet = userSet;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserRole [roleId=" + roleId + ", role=" + role + "]";
	}
	
	
}
