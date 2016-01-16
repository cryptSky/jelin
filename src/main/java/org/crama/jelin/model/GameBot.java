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
@Table(name = "game_bot")
public class GameBot implements Serializable{
	
	private static final long serialVersionUID = 4455599900000532111L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private int id;
	
	@Column(name="NICKNAME", nullable=false, unique=true)
	private String nickname;
	
	@Column(name="AVATAR", nullable=false)
	private String avatar;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "CHARACTER_ID")
	private Character character;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="DIFFICULTY_ID")
	private Difficulty difficulty;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="CATEGORY_ID")
	private Category category;
	
	@Column(name="POINTS", nullable=false)
	private int points;
	
	@Column(name="ENHANCEMENTS", nullable=false)
	private String enhacements;
	
	@Column(name="READING_SPEED", nullable=false)
	private double readingSpeed;
	
	@Column(name="ANSWER_PROBABILITY", nullable=false)
	private double answerProbability;

	public GameBot()
	{
		
	}
	
	public GameBot(String nickname, String avatar, Character character, Difficulty difficulty, Category category,
			int points, String enhacements, double readingSpeed, double answerProbability) {
		super();
		this.nickname = nickname;
		this.avatar = avatar;
		this.character = character;
		this.difficulty = difficulty;
		this.category = category;
		this.points = points;
		this.enhacements = enhacements;
		this.readingSpeed = readingSpeed;
		this.answerProbability = answerProbability;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getEnhacements() {
		return enhacements;
	}

	public void setEnhacements(String enhacements) {
		this.enhacements = enhacements;
	}

	public double getReadingSpeed() {
		return readingSpeed;
	}

	public void setReadingSpeed(double readingSpeed) {
		this.readingSpeed = readingSpeed;
	}

	public double getAnswerProbability() {
		return answerProbability;
	}

	public void setAnswerProbability(double answerProbability) {
		this.answerProbability = answerProbability;
	}

}
