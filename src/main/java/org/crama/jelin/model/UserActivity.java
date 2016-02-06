package org.crama.jelin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.crama.jelin.model.Constants.InviteStatus;

@Entity
@Table(name = "user_activity")
public class UserActivity implements Serializable {
	
	private static final long serialVersionUID = -8259529974748766789L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private int id;
	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY) 
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@Column(name = "LAST_LOGIN", nullable = true)
	private Date lastLogin;
	
	@Column(name = "LAST_TIME_ONLINE", nullable = true)
	private Date lastTimeOnline;
	
	@Column(name = "LAST_GAME", nullable = true)
	private Date lastGame;
	
	@Column(name = "LAST_INVITE", nullable = true)
	private Date lastInvite;
	
	@Column(name = "LAST_INVITE_RESPONSE", nullable = true)
	private InviteStatus lastInviteResponse;

	public UserActivity() {
		
	}

	public UserActivity(User u) {
		this.user = u;
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

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getLastTimeOnline() {
		return lastTimeOnline;
	}

	public void setLastTimeOnline(Date lastTimeOnline) {
		this.lastTimeOnline = lastTimeOnline;
	}

	public Date getLastGame() {
		return lastGame;
	}

	public void setLastGame(Date lastGame) {
		this.lastGame = lastGame;
	}

	public Date getLastInvite() {
		return lastInvite;
	}

	public void setLastInvite(Date lastInvite) {
		this.lastInvite = lastInvite;
	}

	public InviteStatus getLastInviteResponse() {
		return lastInviteResponse;
	}

	public void setLastInviteResponse(InviteStatus lastInviteResponse) {
		this.lastInviteResponse = lastInviteResponse;
	}
	
	
}
