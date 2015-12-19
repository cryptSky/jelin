
package org.crama.jelin.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_model")
public class UserModel implements Serializable {

	private static final long serialVersionUID = -5360750778811586440L;
	
	@Id
	@Column(name="USERNAME", nullable=false)
	private String username;
	
	@Column(name="PASSWORD", nullable=false)
	private String password;
	
	@Column(name="EMAIL", nullable=false)
	private String email;
	
	@Column(name="USER_ROLE", nullable=false)
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "user_role", 
    joinColumns = { 
           @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
    }, 
    inverseJoinColumns = { 
           @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
    })
	private Set<UserRole> roles;
	
	public UserModel() {}
	
	public UserModel(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserModel [username=" + username + ", email=" + email + ", password=" + password + "]";
	}
	
	
}
