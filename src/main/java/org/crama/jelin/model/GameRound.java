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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.crama.jelin.model.Constants.Readiness;

import org.springframework.transaction.annotation.Transactional;


@Entity
@Table(name = "game_round")
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
	
	@Column(name = "PLAYER1_QUESTION_NUM", nullable = false)
	private int player1QuestionNumber;
	
	@Column(name = "PLAYER2_QUESTION_NUM", nullable = false)
	private int player2QuestionNumber;
	
	@Column(name = "PLAYER3_QUESTION_NUM", nullable = false)
	private int player3QuestionNumber;
	
	@Column(name = "PLAYER4_QUESTION_NUM", nullable = false)
	private int player4QuestionNumber;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "round_question", 
	   joinColumns = { 
	          @JoinColumn(name = "ROUND_ID", unique = false)
	   }, 
	   inverseJoinColumns = { 
	          @JoinColumn(name = "QUESTION_ID", unique = false)
	   })
	private List<Question> questions = new ArrayList<Question>();
	
	@Column(name = "ANSWER_COUNT")
	private int humanAnswerCount = 0;
	
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
	
	public int getPlayer1QuestionNumber() {
		return player1QuestionNumber;
	}

	public void setPlayer1QuestionNumber(int player1QuestionNumber) {
		this.player1QuestionNumber = player1QuestionNumber;
	}

	public int getPlayer2QuestionNumber() {
		return player2QuestionNumber;
	}

	public void setPlayer2QuestionNumber(int player2QuestionNumber) {
		this.player2QuestionNumber = player2QuestionNumber;
	}

	public int getPlayer3QuestionNumber() {
		return player3QuestionNumber;
	}

	public void setPlayer3QuestionNumber(int player3QuestionNumber) {
		this.player3QuestionNumber = player3QuestionNumber;
	}

	public int getPlayer4QuestionNumber() {
		return player4QuestionNumber;
	}

	public void setPlayer4QuestionNumber(int player4QuestionNumber) {
		this.player4QuestionNumber = player4QuestionNumber;
	}


	@Transactional
	public Question getQuestion(int index, int questionNumber)
	{
		if (index < questionNumber && index < questions.size() && index >= 0) {
			return questions.get(index);
		}
		else return null;
	}
	
	public void addQuestion(Question question)
	{
		questions.add(question);
	}

	public int getHumanAnswerCount() {
		return humanAnswerCount;
	}

	public void setHumanAnswerCount(int answerCount) {
		this.humanAnswerCount = answerCount;
	}
	
	@Transactional
	public boolean alreadyGotQuestion(User player)
	{
		boolean result = player.getReadiness() == Readiness.QUESTION;
		return result;
	}
	
	@Transactional
	public int currentQuestionNumber()
	{		
		int maxQuestionNumber = getMaxQuestionNumber();
	    			
		return maxQuestionNumber;
	}
	
	@Transactional
	private int getMaxQuestionNumber()
	{
		int playerCount = game.getPlayersCount();
		int maxQuestionNumber = -1;
		if (playerCount == 2)
		{
			maxQuestionNumber = Math.max(getPlayer1QuestionNumber(), getPlayer2QuestionNumber());					 
		}
		else if (playerCount == 3)
		{
			maxQuestionNumber = Math.max(Math.max(getPlayer1QuestionNumber(), getPlayer2QuestionNumber()),
					 									getPlayer3QuestionNumber());
		} else if (playerCount == 4)
		{
			maxQuestionNumber = Math.max(Math.max(getPlayer1QuestionNumber(), getPlayer2QuestionNumber()),
					 Math.max(getPlayer3QuestionNumber(), getPlayer4QuestionNumber()));
		
		}
		
		return maxQuestionNumber;
	}
	
	@Transactional
	public int getQuestionNumber(User player)
	{
		int id = game.getPlayerNumberByUser(player);
		int result = 0;
		switch (id)
		{
			case 1: result = getPlayer1QuestionNumber();
					break;
			case 2: result = getPlayer2QuestionNumber();
					break;
			case 3: result = getPlayer3QuestionNumber();
					break;
			case 4: result = getPlayer4QuestionNumber();
					break;
		}
		
		return result;
	}
	
	@Transactional
	public void setQuestionNumber(User player, int qnumber)
	{
		int id = game.getPlayerNumberByUser(player);
		switch (id)
		{
			case 1: setPlayer1QuestionNumber(qnumber);
					break;
			case 2: setPlayer2QuestionNumber(qnumber);
					break;
			case 3: setPlayer3QuestionNumber(qnumber);
					break;
			case 4: setPlayer4QuestionNumber(qnumber);
					break;
		}
	}
	

	@Transactional
	public boolean endOfRound(int questionNumber)
	{
		boolean result = false;
		int playerCount = game.getPlayersCount();
		if (playerCount == 2)
		{
			result = getPlayer1QuestionNumber() == questionNumber &&
					 getPlayer1QuestionNumber() == getPlayer2QuestionNumber();
		}
		else if (playerCount == 3)
		{
			result = getPlayer1QuestionNumber() == questionNumber &&
					 getPlayer1QuestionNumber() == getPlayer2QuestionNumber() &&
					 getPlayer2QuestionNumber() == getPlayer3QuestionNumber();
		}
		else if (playerCount == 4)
		{
			result = getPlayer1QuestionNumber() == questionNumber &&
					 getPlayer1QuestionNumber() == getPlayer2QuestionNumber() &&
					 getPlayer2QuestionNumber() == getPlayer3QuestionNumber() &&
					 getPlayer3QuestionNumber() == getPlayer4QuestionNumber();
		}
				
		return result;
	}
	
	@Transactional
	public void writeResult(QuestionResult result)
	{
		User player = result.getPlayer();
		int playerNumber = game.getPlayerNumberByUser(player);
		switch(playerNumber)
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
