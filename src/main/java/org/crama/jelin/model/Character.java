package org.crama.jelin.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "GameCharacter")
public class Character implements Serializable {
	
	private static final long serialVersionUID = -7100638025931949913L;
	
	@Id
	@Column(name="CHARACTER_ID", nullable=false, unique = true)
	private int id;
	
	@Column(name="SPECIAL", nullable=false)
	private boolean special;
	
	@Column(name="ACRONS", nullable=false)
	private int acrons;
	
	@Column(name="GOLD_ACRONS", nullable=false)
	private int goldAcrons;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="DESCRIPTION", nullable=true)
	private String description;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "characterSet")
	private Set<User> userSet = new HashSet<User>();
	
	public Character() {
		
	}
	
	public Character(int id, boolean special, int acrons, int goldAcrons, String name, String description) {
		super();
		this.id = id;
		this.special = special;
		this.acrons = acrons;
		this.goldAcrons = goldAcrons;
		this.name = name;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isSpecial() {
		return special;
	}
	public void setSpecial(boolean special) {
		this.special = special;
	}
	public int getAcrons() {
		return acrons;
	}
	public void setAcrons(int acrons) {
		this.acrons = acrons;
	}
	public int getGoldAcrons() {
		return goldAcrons;
	}
	public void setGoldAcrons(int goldAcrons) {
		this.goldAcrons = goldAcrons;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	@Override
	public String toString() {
		return "Character [id=" + id + ", special=" + special + ", acrons=" + acrons + ", goldAcrons=" + goldAcrons
				+ ", name=" + name + ", description=" + description + ", userSet=" + userSet + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Character other = (Character) obj;
		if (id != other.id)
			return false;
		return true;
	}



	
	
}
