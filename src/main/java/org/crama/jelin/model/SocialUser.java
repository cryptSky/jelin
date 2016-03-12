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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "social_user")
public class SocialUser implements Serializable {

	private static final long serialVersionUID = 130762318871912994L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, unique = true)
	private int id;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "USER_ID", nullable = false, updatable = false)
	private User user;

	@Column(name = "PROVIDER_ID", nullable = false)
	private String providerId;

	@Column(name = "PROVIDER_USER_ID", nullable = false)
	private String providerUserId;

	@Column(name = "ACCESS_TOKEN", nullable = false)
	private String accessToken;
	
	@Column(name = "SECRET", nullable = true)
	private String secret;
	
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	  
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	  
	@Column(name = "EMAIL", nullable = true)
	private String email;
	  
	@Column(name = "PHONE", nullable = true)
	private String phone;
	  
	  /*@Transient
	  private int age;
	  
	  @Transient
	  private String gender;
	  
	  @Transient
	  private String locaity;
	  
	  @Transient
	  private String region;
	  
	  @Transient
	  private String country;*/
	  

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

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "SocialUser [id=" + id + ", user=" + user + ", providerId=" + providerId + ", providerUserId="
				+ providerUserId + ", accessToken=" + accessToken + ", secret=" + secret + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + "]";
	}
	
	

	/*public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLocaity() {
		return locaity;
	}

	public void setLocaity(String locaity) {
		this.locaity = locaity;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}*/


}
