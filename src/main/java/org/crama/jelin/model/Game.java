package org.crama.jelin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.crama.jelin.model.Constants.GameState;

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
	@JoinColumn(name="THEME_ID", nullable=false)
	private Category theme;
	
	@Column(name="IS_RANDOM", nullable=false)
	private boolean random;
	
	@JsonIgnore
	@Column(name = "STATE")
	private GameState gameState;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="DIFFICULTY_ID")
	private Difficulty difficulty;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="CREATOR_ID", nullable = false)
	private User creator;
	
	@JsonIgnore
	@Column(name = "PLAYER1_POINTS", nullable = false)
	private int player1Points;
	
	@JsonIgnore
	@Column(name = "PLAYER2_POINTS", nullable = false)
	private int player2Points;
	
	@JsonIgnore
	@Column(name = "PLAYER3_POINTS", nullable = false)
	private int player3Points;
	
	@JsonIgnore
	@Column(name = "PLAYER4_POINTS")
	private int player4Points;
	
	@JsonIgnore
	@Column(name = "HOST")
	private int host;
	
	@JsonIgnore
	@Column(name = "ROUND")
	private int round;
	
	@JsonIgnore
	private ArrayList<Integer> hostOrder;
	
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
	
	
	/*@JsonIgnore
	@OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<GameUser> gamePlayerSet = new HashSet<GameUser>(); 
	*/
	public Game() {}

	public Game(Category theme, boolean isRandom) {
		super();
		this.theme = theme;
		this.random = isRandom;		
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

	public int getPlayer1Points() {
		return player1Points;
	}

	public void setPlayer1Points(int player1Points) {
		this.player1Points = player1Points;
	}

	public int getPlayer2Points() {
		return player2Points;
	}

	public void setPlayer2Points(int player2Points) {
		this.player2Points = player2Points;
	}

	public int getPlayer3Points() {
		return player3Points;
	}

	public void setPlayer3Points(int player3Points) {
		this.player3Points = player3Points;
	}

	public int getPlayer4Points() {
		return player4Points;
	}

	public void setPlayer4Points(int player4Points) {
		this.player4Points = player4Points;
	}

	public int getHost() {
		return host;
	}

	public void setHost(int host) {
		this.host = host;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public ArrayList<Integer> getHostOrder() {
		return hostOrder;
	}

	public void setHostOrder(ArrayList<Integer> hostOrder) {
		this.hostOrder = hostOrder;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", theme=" + theme + ", isRandom=" + random + ", gameState=" + gameState
				+ ", difficulty=" + difficulty + "]";
	}
	
	
}
