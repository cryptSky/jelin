/*package org.crama.jelin.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GameUser")
public class GameUser {
	
	@Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "GAME_ID")
	private Game game;
	
	@Column(name = "CREATOR", nullable = false)
	private boolean creator;

	public GameUser() {
		
	}
	
	public GameUser(User user, Game game, boolean creator) {
		super();
		this.user = user;
		this.game = game;
		this.creator = creator;
	}

	public GameUser(int id, User user, Game game, boolean creator) {
		super();
		this.id = id;
		this.user = user;
		this.game = game;
		this.creator = creator;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isCreator() {
		return creator;
	}

	public void setCreator(boolean creator) {
		this.creator = creator;
	}

	
}
*/