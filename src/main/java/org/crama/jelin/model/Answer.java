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
@Table(name = "answer")
public class Answer implements Serializable {
	
	private static final long serialVersionUID = -33339114983899222L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ROUND_ID")
	private GameRound round;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_ID")
	private Question question;
	
	@Column(name = "PLAYER_NUMBER")
	private int playerNumber;
	
	@Column(name = "VARIANT")
	private int variant;
	
	@Column(name = "TIME")
	private int time;
	
	public Answer()
	{
		
	}

	public Answer(GameRound round, Question question, int playerNumber, int variant, int time) {
		super();
		this.round = round;
		this.question = question;
		this.playerNumber = playerNumber;
		this.variant = variant;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GameRound getRound() {
		return round;
	}

	public void setRound(GameRound round) {
		this.round = round;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public int getVariant() {
		return variant;
	}

	public void setVariant(int variant) {
		this.variant = variant;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	
}
