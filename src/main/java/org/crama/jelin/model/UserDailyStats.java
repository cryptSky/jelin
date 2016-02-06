package org.crama.jelin.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user_daily_stats")
public class UserDailyStats implements Serializable {
	
	private static final long serialVersionUID = -2373929802285599646L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private int id;
	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY) 
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE", nullable = false)
	private Date date;
 	
	@Column(name = "GAMES_INITIATED")
	private int gamesInitiated;
	
	@Column(name = "GAMES_PLAYED")
	private int gamesPlayed;
	
	@Column(name = "SMM_INVITES")
	private int smmInvites;
	
	@Column(name = "SMM_SHARES")
	private int smmShares;


	public UserDailyStats(User u) {
		this.user = u;
		this.date = new Date();
	}
	
	public UserDailyStats() {
		this.date = new Date();
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public void clear() {
		this.date = new Date();
		this.gamesInitiated = 0;
		this.gamesPlayed = 0;
		this.smmInvites = 0;
		this.smmShares = 0;
		
	}

	
	
	
}
