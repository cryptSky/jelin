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
@Table(name = "score_summary")
public class ScoreSummary implements Serializable, Comparable<ScoreSummary> {

	private static final long serialVersionUID = -4895732224356779322L;
	
	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
	private int id;
	
	@Column(name="SCORE", nullable=false)
	private int score;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
	private User user;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="GAME_ID")
	private Game game;
	
	@Column(name = "ACRONS")
	private int acrons;
	
	@Column(name = "CORRECT_ANSWERS")
	private int correctAnswers;
	
	@Column(name = "WRONG_ANSWERS")
	private int wrongAnswers;
	
	public ScoreSummary()
	{
		
	}

	public ScoreSummary(int score, User user, Game game) {
		super();
		this.score = score;
		this.user = user;
		this.game = game;
		this.acrons = (int)(score * 0.43);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public int getAcrons() {
		return acrons;
	}

	public void setAcrons(int acrons) {
		this.acrons = acrons;
	}
	
	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public int getWrongAnswers() {
		return wrongAnswers;
	}

	public void setWrongAnswers(int wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}

	@Override
	public int compareTo(ScoreSummary o) {
		
		return this.score - o.score;
	}
}
