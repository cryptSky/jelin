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
@Table(name = "bonus")
public class Bonus implements Serializable {

	private static final long serialVersionUID = -6528235087362880976L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private int id;
	
	@Column(name = "BONUS_LIMIT", nullable = false)
	private int limit = 0;
	
	@Column(name = "DAILY_LIMIT", nullable = false)
	private int dailyLimit = 0;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "DESCRIPTION", nullable = true)
	private String description;
	
	@Column(name = "IMAGE", nullable = false)
	private String image;
	
	@Column(name="FOR_EARLY_REGISTER", nullable = true)
	private Boolean forEarlyRegister;
	
	@Column(name = "COND_DAYS", nullable = true)
	private Integer condDays; 
	
	@Column(name = "COND_INVITES", nullable = true)
	private Integer condInvites;
	
	@Column(name = "COND_SHARES", nullable = true)
	private Integer condShares;
	
	@Column(name = "COND_INITIATED", nullable = true)
	private Integer condInitiated;
	
	@Column(name = "COND_PLAYED", nullable = true)
	private Integer condPlayed;
	
	@Column(name = "COND_PROMOCODE", nullable = true)
	private String condPromocode;
	
	@Column(name = "ACORNS", nullable = true)
	private Integer acorns;
	
	@Column(name = "GOLD_ACORNS", nullable = true)
	private Integer goldAcorns;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY) 
	@JoinColumn(name = "CHARACTER_ID", nullable = true)
	private Character character;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY) 
	@JoinColumn(name = "ENHANCER_ID", nullable = true)
	private Enhancer enhancer;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY) 
	@JoinColumn(name = "GROUP_ID", nullable = true)
	private Group group;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(int dailyLimit) {
		this.dailyLimit = dailyLimit;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getCondDays() {
		return condDays;
	}

	public void setCondDays(Integer condDays) {
		this.condDays = condDays;
	}

	public Integer getCondInvites() {
		return condInvites;
	}

	public void setCondInvites(Integer condInvites) {
		this.condInvites = condInvites;
	}

	public Integer getCondShares() {
		return condShares;
	}

	public void setCondShares(Integer condShares) {
		this.condShares = condShares;
	}

	public Integer getCondInitiated() {
		return condInitiated;
	}

	public void setCondInitiated(Integer condInitiated) {
		this.condInitiated = condInitiated;
	}

	public Integer getCondPlayed() {
		return condPlayed;
	}

	public void setCondPlayed(Integer condPlayed) {
		this.condPlayed = condPlayed;
	}

	public String getCondPromocode() {
		return condPromocode;
	}

	public void setCondPromocode(String condPromocode) {
		this.condPromocode = condPromocode;
	}

	public Integer getAcorns() {
		return acorns;
	}

	public void setAcorns(Integer acorns) {
		this.acorns = acorns;
	}

	public Integer getGoldAcorns() {
		return goldAcorns;
	}

	public void setGoldAcorns(Integer goldAcorns) {
		this.goldAcorns = goldAcorns;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Enhancer getEnhancer() {
		return enhancer;
	}

	public void setEnhancer(Enhancer enhancer) {
		this.enhancer = enhancer;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Boolean getForEarlyRegister() {
		return forEarlyRegister;
	}

	public void setForEarlyRegister(Boolean forEarlyRegister) {
		this.forEarlyRegister = forEarlyRegister;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Bonus other = (Bonus) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
