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
@Table(name = "GameRound")
public class GameRound implements Serializable {
	
	private static final long serialVersionUID = 199455653224357L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="GAME_ID")
	private Game game;
	
	@Column(name = "ROUND_NUMBER")
	private int roundNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOST")
	private User host;
	
	@Column(name = "PLAYER1_POINTS", nullable = false)
	private int player1Points;
	
	@Column(name = "PLAYER2_POINTS", nullable = false)
	private int player2Points;
	
	@Column(name = "PLAYER3_POINTS", nullable = false)
	private int player3Points;
	
	@Column(name = "PLAYER4_POINTS", nullable = false)
	private int player4Points;
	
	public GameRound()
	{
		
	}

	public GameRound(Game game, int roundNumber, User host) {
		super();
		this.game = game;
		this.roundNumber = roundNumber;
		this.host = host;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}
	
	
	
	
}
