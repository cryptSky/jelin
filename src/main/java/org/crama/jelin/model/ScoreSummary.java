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
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
	private User user;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name="GAME_ID")
	private Game game;
	
	@Column(name = "ACORNS")
	private int acorns;
	
	@Column(name = "CORRECT_ANSWERS")
	private long correctAnswers;

	@Column(name = "WRONG_ANSWERS")
	private long wrongAnswers;
	
	public ScoreSummary()
	{
		
	}

	public ScoreSummary(int score, User user, Game game, long wrongAnswers, long rightAnswers) {
		super();
		this.score = score;
		this.user = user;
		this.game = game;
		this.acorns = (int)(score * 0.43);
		this.correctAnswers = rightAnswers;
		this.wrongAnswers = wrongAnswers;
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

	public int getAcorns() {
		return acorns;
	}

	public void setAcorns(int acorns) {
		this.acorns = acorns;
	}
	
	public long getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(long correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public long getWrongAnswers() {
		return wrongAnswers;
	}

	public void setWrongAnswers(long wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}

	@Override
	public int compareTo(ScoreSummary o) {
		
		return o.score - this.score;
	}
}
