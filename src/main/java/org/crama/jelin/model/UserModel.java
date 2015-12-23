
package org.crama.jelin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "UserModel")
public class UserModel implements Serializable {

	private static final long serialVersionUID = -5360750778811586440L;
	
	@Id
	@Column(name="USERNAME", nullable=false, unique=true)
	@NotNull(message="Username should not be empty")
	@Size(min=2, max=42, message="Username size should be from 2 to 42 characters")
	@Pattern(regexp="[@._A-Za-z0-9-]{1,}", message="Wrong username pattern")
	private String username;
	
	@Column(name="PASSWORD", nullable=false)
	@NotNull(message="Password should not be empty")
	@Size(min=2, max=42, message="Password size should be from 2 to 42 characters")
	@Pattern(regexp="[@._A-Za-z0-9-]{1,}", message="Wrong password pattern")
	private String password;
	
	@Column(name="EMAIL", nullable=false, unique=true)
	@NotNull(message="Password should not be empty")
	@Size(min=6, message="Password should be from 6 characters long")
	@Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Wrong email pattern")
	private String email;
	
	/*@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", 
    joinColumns = { 
           @JoinColumn(name = "USERNAME")
    }, 
    inverseJoinColumns = { 
           @JoinColumn(name = "ROLE_ID")
    })
	private Set<UserRole> roles = new HashSet<UserRole>();*/
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false)
	private UserRole role;
	
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
	
	/*public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}*/

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserModel [username=" + username + ", email=" + email + ", password=" + password + "]";
	}

}
