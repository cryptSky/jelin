package org.crama.jelin.model;

import java.io.Serializable;
import org.crama.jelin.model.Constants.InviteStatus;

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
@Table(name = "GameOpponent")
public class GameOpponent implements Serializable {
	
	private static final long serialVersionUID = 1107942852683349457L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "GAME_ID")
	private Game game;
	
	/*@Column(name = "CREATOR", nullable = false)
	private boolean creator;*/

	@JoinColumn(name = "STATUS")
	private InviteStatus inviteStatus;
	
	public GameOpponent() {
		
	}
	
	public GameOpponent(User user, Game game, InviteStatus status) {
		super();
		this.user = user;
		this.game = game;
		this.inviteStatus = status;
	}

	public GameOpponent(int id, User user, Game game, InviteStatus status) {
		super();
		this.id = id;
		this.user = user;
		this.game = game;
		this.inviteStatus = status;
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

	public InviteStatus getInviteStatus() {
		return inviteStatus;
	}

	public void setInviteStatus(InviteStatus inviteStatus) {
		this.inviteStatus = inviteStatus;
	}

	@Override
	public String toString() {
		return "GameOpponent [id=" + id + ", user=" + user + ", game=" + game + ", inviteStatus=" + inviteStatus + "]";
	}

	/*public boolean isCreator() {
		return creator;
	}

	public void setCreator(boolean creator) {
		this.creator = creator;
	}*/

	
	
}
