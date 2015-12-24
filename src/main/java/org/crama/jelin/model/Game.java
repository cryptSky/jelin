package org.crama.jelin.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@NamedNativeQueries({
	@NamedNativeQuery(
	name = "getCreatedGameSQL",
	query = "SELECT * FROM game " +
			"WHERE STATE_ID = ( " +
			"SELECT STATE_ID FROM gamestate " +
			"WHERE STATE = :state) " +
		"AND ID = ( " +
			"SELECT GAME_ID FROM gameuser " +
			"WHERE USER_ID = :userId);",
        resultClass = Game.class
	)
})
@Entity
@Table(name = "Game")
public class Game implements Serializable {
	
	private static final long serialVersionUID = -99467490547468311L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="THEME_ID", nullable=false)
	private Category theme;
	
	@Column(name="IS_RANDOM", nullable=false)
	private boolean isRandom;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_ID")
	private GameState gameState;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="DIFFICULTY_ID")
	private Difficulty difficulty;
	
	@JsonIgnore
	@OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<GameUser> gamePlayerSet = new HashSet<GameUser>(); 
	
	public Game() {}

	public Game(Category theme, boolean isRandom) {
		super();
		this.theme = theme;
		this.isRandom = isRandom;		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getTheme() {
		return theme;
	}

	public void setTheme(Category theme) {
		this.theme = theme;
	}

	public boolean isRandom() {
		return isRandom;
	}

	public void setRandom(boolean isRandom) {
		this.isRandom = isRandom;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Set<GameUser> getGamePlayerSet() {
		return gamePlayerSet;
	}

	public void setGamePlayerSet(Set<GameUser> gamePlayerSet) {
		this.gamePlayerSet = gamePlayerSet;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", theme=" + theme + ", isRandom=" + isRandom + ", gameState=" + gameState
				+ ", difficulty=" + difficulty + ", gamePlayerSet=" + gamePlayerSet + "]";
	}
	
	
	
}
