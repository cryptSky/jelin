package org.crama.jelin.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "game_character")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Character implements Serializable {
	
	private static final long serialVersionUID = -7100638025931949913L;
	
	@Id
	@Column(name="CHARACTER_ID", nullable=false, unique = true)
	private int id;
	
	@Column(name="SPECIAL", nullable=false)
	private boolean special;
	
	@Column(name="ACORNS", nullable=false)
	private int acorns;
	
	@Column(name="GOLD_ACORNS", nullable=false)
	private int goldAcorns;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="DESCRIPTION", nullable=true)
	private String desc;
	
	@OneToMany(mappedBy = "character", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private List<ImageLayer> imageLayerList;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "characterSet")
	private Set<User> userSet = new HashSet<User>();
	
	public Character() {
		
	}
	
	public Character(int id, boolean special, int acorns, int goldAcorns, String name, String description) {
		super();
		this.id = id;
		this.special = special;
		this.acorns = acorns;
		this.goldAcorns = goldAcorns;
		this.name = name;
		this.desc = description;
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
	public int getAcorns() {
		return acorns;
	}
	public void setAcorns(int acorns) {
		this.acorns = acorns;
	}
	public int getGoldAcorns() {
		return goldAcorns;
	}
	public void setGoldAcorns(int goldAcorns) {
		this.goldAcorns = goldAcorns;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Set<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	public List<ImageLayer> getImageLayerList() {
		return imageLayerList;
	}

	public void setImageLayerList(List<ImageLayer> imageLayerList) {
		this.imageLayerList = imageLayerList;
	}

	@Override
	public String toString() {
		return "Character [id=" + id + ", special=" + special + ", acorns=" + acorns + ", goldAcorns=" + goldAcorns
				+ ", name=" + name + ", description=" + desc + ", userSet=" + userSet + "]";
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
