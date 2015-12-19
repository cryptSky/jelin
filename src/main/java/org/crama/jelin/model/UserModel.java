package org.crama.jelin.model;

import java.io.Serializable;

public class UserModel implements Serializable {

	private static final long serialVersionUID = -5360750778811586440L;
	
	private String username;
	private String password;
	private String email;
	
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
	
	@Override
	public String toString() {
		return "UserModel [username=" + username + ", email=" + email + ", password=" + password + "]";
	}
	
	
}
