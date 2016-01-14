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


@Entity
@Table(name = "enhancer")
public class Enhancer implements Serializable {

	private static final long serialVersionUID = -9128111025867791649L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER) 
	@JoinColumn(name = "CHARACTER_ID", nullable = false)
	private Character character;
	
	@Column(name = "IS_EQUIPMENT")
	private boolean equipment;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "DESCRIPTON", nullable = true)
	private String description;
	
	@Column(name = "ICON", nullable = false)
	private String icon;
	
	@Column(name = "POINTS_PERCENT", nullable = false)
	private int pointsPercent;
	
	@Column(name = "ACORNS_PERCENT", nullable = false)
	private int acornsPercent;
	
	@Column(name = "ACORNS", nullable = false)
	private int acorns;
	
	@Column(name = "GOLD_ACORNS", nullable = false)
	private int goldAcorns;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public boolean isEquipment() {
		return equipment;
	}

	public void setEquipment(boolean equipment) {
		this.equipment = equipment;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getPointsPercent() {
		return pointsPercent;
	}

	public void setPointsPercent(int pointsPercent) {
		this.pointsPercent = pointsPercent;
	}

	public int getAcornsPercent() {
		return acornsPercent;
	}

	public void setAcornsPercent(int acornsPercent) {
		this.acornsPercent = acornsPercent;
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

	@Override
	public String toString() {
		return "Enhancer [id=" + id + ", character=" + character + ", equipment=" + equipment + ", name=" + name
				+ ", description=" + description + ", icon=" + icon + ", pointsPercent=" + pointsPercent
				+ ", acornsPercent=" + acornsPercent + ", acorns=" + acorns + ", goldAcorns=" + goldAcorns + "]";
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
		Enhancer other = (Enhancer) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
