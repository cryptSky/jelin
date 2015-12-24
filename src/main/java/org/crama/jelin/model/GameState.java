package org.crama.jelin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GameState")
public class GameState {
	
	public static final String CREATED = "Created";
	public static final String IN_PROGRESS = "In progress";
	public static final String ENDED = "Ended";
	
	@Id
	@GeneratedValue
	@Column(name="STATE_ID", nullable=false)
	private int stateId;
	@Column(name="STATE", nullable=false)
	private String state;
	
	public GameState() {
		
	}
	
	public GameState(String state) {
		super();
		this.state = state;
	}

	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
}
