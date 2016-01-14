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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "QuestionResult")
public class QuestionResult implements Serializable {

	private static final long serialVersionUID = -777778292929211111L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
	private int id;
	
	@Column(name = "ANSWER")
	private int answer;
	
	@Column(name = "SCORE")
	private int score;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_ID")
	private Question question;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ROUND_ID")
	private GameRound gameRound;
	
	@JsonIgnore
	@Column(name = "PLAYER_NUMBER")
	private int playerNumber;
	
	public QuestionResult()
	{
		
	}

	public QuestionResult(int answer, int score, Question question, GameRound gameRound, int playerNumber) {
		super();
		this.answer = answer;
		this.score = score;
		this.question = question;
		this.gameRound = gameRound;
		this.playerNumber = playerNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public GameRound getGameRound() {
		return gameRound;
	}

	public void setGameRound(GameRound gameRound) {
		this.gameRound = gameRound;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	
}
