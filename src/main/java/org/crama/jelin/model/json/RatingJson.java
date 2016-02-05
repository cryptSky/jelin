package org.crama.jelin.model.json;

public class RatingJson {
	
	private int number;
	private int points;
	private String avatar;
	private String username;
	
	
	public RatingJson(int number, int points, String avatar, String username) {
		super();
		this.number = number;
		this.points = points;
		this.avatar = avatar;
		this.username = username;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
