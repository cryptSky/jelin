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
@Table(name = "Question")
public class Question implements Serializable {
	
	private static final long serialVersionUID = -4453888888834781L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
	private int id;
	
	@Column(name="QUESTION", nullable=false)
	private String question;
	
	@Column(name="VAR1", nullable=false)
	private String var1;
	
	@Column(name="VAR2", nullable=false)
	private String var2;
	
	@Column(name="VAR3", nullable=false)
	private String var3;
	
	@Column(name="VAR4", nullable=false)
	private String var4;
	
	@Column(name="ANSWER", nullable=false)
	private int answer;
	
	@Column(name="TIME_A", nullable=false)
	private int time;
	
	@JsonIgnore
	@Column(name="TIME_B", nullable=false)
	private int timeB;
	
	@JsonIgnore
	@Column(name="TIME_C", nullable=false)
	private int timeC;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="DIFFICULTY_ID")
	private Difficulty difficulty;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="CATEGORY_ID")
	private Category category;

	public Question()
	{
		
	}
	
	public Question(String question, String var1, String var2, String var3, String var4, int answer, int time,
			int timeB, int timeC, Difficulty difficulty, Category category) {
		super();
		this.question = question;
		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
		this.var4 = var4;
		this.answer = answer;
		this.time = time;
		this.timeB = timeB;
		this.timeC = timeC;
		this.difficulty = difficulty;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getVar1() {
		return var1;
	}

	public void setVar1(String var1) {
		this.var1 = var1;
	}

	public String getVar2() {
		return var2;
	}

	public void setVar2(String var2) {
		this.var2 = var2;
	}

	public String getVar3() {
		return var3;
	}

	public void setVar3(String var3) {
		this.var3 = var3;
	}

	public String getVar4() {
		return var4;
	}

	public void setVar4(String var4) {
		this.var4 = var4;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTimeB() {
		return timeB;
	}

	public void setTimeB(int timeB) {
		this.timeB = timeB;
	}

	public int getTimeC() {
		return timeC;
	}

	public void setTimeC(int timeC) {
		this.timeC = timeC;
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
	
	
}
