package org.crama.jelin.model;

import java.io.Serializable;

public class ScoreSummary implements Serializable {

	private static final long serialVersionUID = -4895732224356779322L;
	
	private int score;
	
	private User user;
	
	public ScoreSummary()
	{
		
	}

	public ScoreSummary(int score, User user) {
		super();
		this.score = score;
		this.user = user;
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
}
