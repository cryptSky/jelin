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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_statistics")
public class UserStatistics implements Serializable {

	private static final long serialVersionUID = 6935587254516250894L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private int id;
	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY) 
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@Column(name = "DAYS_IN_GAME")
	private int daysInGame = 0;
	
	@Column(name = "POINTS")
	private int points = 0;
	
	@Column(name = "ACRONS")
	private int acrons = 0;
	
	@Column(name = "GOLD_ACRONS")
	private int goldAcrons = 0;
	
	@Column(name = "MONEY_SPENT")
	private int moneySpent = 0;
	
	@Column(name = "GAMES_INITIATED")
	private int gamesInitiated = 0;
	
	@Column(name = "GAMES_PLAYED")
	private int gamesPlayed = 0;
	
	@Column(name = "SMM_INVITES")
	private int smmInvites = 0;
	
	@Column(name = "SMM_SHARES")
	private int smmShares = 0;
	
	@Column(name = "RATING")
	private int rating = 0;

	public UserStatistics() {
		super();
	}

	public UserStatistics(User user) {
		super();
		this.user = user;
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

	public int getDaysInGame() {
		return daysInGame;
	}

	public void setDaysInGame(int daysInGame) {
		this.daysInGame = daysInGame;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
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

	public int getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(int moneySpent) {
		this.moneySpent = moneySpent;
	}

	public int getGamesInitiated() {
		return gamesInitiated;
	}

	public void setGamesInitiated(int gamesInitiated) {
		this.gamesInitiated = gamesInitiated;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public int getSmmInvites() {
		return smmInvites;
	}

	public void setSmmInvites(int smmInvites) {
		this.smmInvites = smmInvites;
	}

	public int getSmmShares() {
		return smmShares;
	}

	public void setSmmShares(int smmShares) {
		this.smmShares = smmShares;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
}
