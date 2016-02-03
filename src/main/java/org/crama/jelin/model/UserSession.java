package org.crama.jelin.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.crama.jelin.model.Constants.Language;

@Entity
@Table(name = "user_session")
public class UserSession implements Serializable {
	
	private static final long serialVersionUID = -2146869564413213548L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="USER_ID", nullable = false)
	private User user;
	
	@Column(name = "LANGUAGE")
	private Language language;
	
	@Column(name = "DEVICE_TOKEN")
	private String deviceToken;
	
	public UserSession()
	{
		
	}
	
	public UserSession(User user, Language language, String deviceToken) {
		super();
		this.user = user;
		this.language = language;
		this.deviceToken = deviceToken;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

}
