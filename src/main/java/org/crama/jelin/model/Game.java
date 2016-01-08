package org.crama.jelin.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.crama.jelin.model.Constants.GameState;

import com.fasterxml.jackson.annotation.JsonIgnore;


/*@NamedNativeQueries({
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
})*/
@Entity
@Table(name = "Game")
public class Game implements Serializable {
	
	private static final long serialVersionUID = -99467490547468311L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="THEME_ID")
	private Category theme;
	
	@Column(name="IS_RANDOM", nullable=false)
	private boolean random;
	
	@Column(name = "STATE")
	private GameState gameState;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="DIFFICULTY_ID")
	private Difficulty difficulty;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="CREATOR_ID", nullable = false)
	private User creator;
	
	@Column(name = "INIT_DATE")
	private Date initDate;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ROUND")
	private GameRound round;
	
	/*@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "GameOpponent", 
    joinColumns = { 
           @JoinColumn(name = "GAME_ID")
    }, 
    inverseJoinColumns = { 
           @JoinColumn(name = "USER_ID")
    })
	private Set<User> gameOpponents = new HashSet<User>(); 
	*/
	
	@JsonIgnore
	@OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<GameOpponent> gameOpponents = new HashSet<GameOpponent>();
	
	
	public Game() {}

	public Game(Category theme, boolean isRandom) {
		super();
		this.theme = theme;
		this.random = isRandom;		
	}

	public Game(Difficulty difficulty) {
		super();
		this.difficulty = difficulty;
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

	public boolean getRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	
	public Set<GameOpponent> getGameOpponents() {
		return gameOpponents;
	}

	public void setGameOpponents(Set<GameOpponent> gameOpponents) {
		this.gameOpponents = gameOpponents;
	}

	public GameRound getRound() {
		return round;
	}

	public void setRound(GameRound round) {
		this.round = round;
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", theme=" + theme + ", isRandom=" + random + ", gameState=" + gameState
				+ ", difficulty=" + difficulty + "]";
	}
	
	
}
