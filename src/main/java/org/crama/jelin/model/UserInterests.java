
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
@Table(name = "user_interests")
public class UserInterests implements Serializable {
	
	private static final long serialVersionUID = -432534658225340000L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="USER_ID", nullable=false)
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="CATEGORY_ID", nullable=false)
	private Category theme;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="DIFFICULTY_ID", nullable=false)
	private Difficulty difficulty;
	
	@Column(name="GAMES_PLAYED", nullable=false)
	private int gamesPlayed;

	public UserInterests(int id, User user, Category theme, Difficulty difficulty, int gamesPlayed) {
		super();
		this.id = id;
		this.user = user;
		this.theme = theme;
		this.difficulty = difficulty;
		this.gamesPlayed = gamesPlayed;
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