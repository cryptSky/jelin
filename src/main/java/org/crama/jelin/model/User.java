
package org.crama.jelin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue
	@Column(name="USER_ID", nullable=false)
	private int id;
	
	@Column(name="USERNAME", nullable=false)
	private String username;
	
	@Column(name="EMAIL", nullable=false)
	private String email;
	
	public User() {}
	
	public User(int id, String username, String email) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
}
