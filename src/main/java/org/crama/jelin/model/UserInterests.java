
package org.crama.jelin.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity  @IdClass(UserInterestsId.class)
@Table(name = "user_interests")
public class UserInterests implements Serializable {
	
	private static final long serialVersionUID = -432534658225340000L;

	@Id
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="USER_ID", nullable=false)
	private User user;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="CATEGORY_ID", nullable=false)
	private Category theme;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="DIFFICULTY_ID", nullable=false)
	private Difficulty difficulty;
	
	@Column(name="GAMES_PLAYED", nullable=false)
	private int gamesPlayed;

	public UserInterests(User user, Category theme, Difficulty difficulty, int gamesPlayed) {
		super();
		this.user = user;
		this.theme = theme;
		this.difficulty = difficulty;
		this.gamesPlayed = gamesPlayed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getTheme() {
		return theme;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public void setTheme(Category theme) {
		this.theme = theme;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	
}

class UserInterestsId implements Serializable
{
	private static final long serialVersionUID = 2596924711253083967L;
	
	User user;
	Category theme;
	Difficulty difficulty;
}