package org.crama.jelin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	private int roundNumber = 0;
	
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
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Question> questions = new ArrayList<Question>();
	
	@Column(name = "QUESTION_NUMBER")
	private int questionNumber = 0;
	
	@Column(name = "ANSWER_COUNT")
	private int answerCount = 0;
	
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
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
	
	public Question getQuestion(int index)
	{
		if (index < Constants.questionsNumber && index < questions.size())
		{
			return questions.get(index);
		}
		else return null;
	}
	
	public void addQuestion(Question question)
	{
		questions.add(question);
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int answerOrderNumber) {
		this.questionNumber = answerOrderNumber;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	
	public void writeResult(QuestionResult result)
	{
		switch(result.getPlayerNumber())
		{
			case 1: setPlayer1Points(getPlayer1Points() + result.getScore()); 
					break;
			case 2: setPlayer2Points(getPlayer2Points() + result.getScore());
					break;
			case 3: setPlayer3Points(getPlayer3Points() + result.getScore());
					break;
			case 4: setPlayer4Points(getPlayer4Points() + result.getScore());
					break;
		}
	}
	
}
