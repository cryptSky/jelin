package org.crama.jelin.model.json;

import java.io.Serializable;

import org.crama.jelin.model.User;
import org.crama.jelin.model.Constants.NetStatus;
import org.crama.jelin.model.Constants.ProcessStatus;

public class UserJson implements Serializable {
	
	private static final long serialVersionUID = -2330165078664732217L;
	
	private int id;
	private String username;
	private String avatar;
	private int gamePoints;
	private ProcessStatus processStatus;
	private NetStatus netStatus;
	
	public UserJson(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.avatar = user.getUserInfo().getAvatar();
		this.gamePoints = user.getUserStatistics().getPoints();
		this.processStatus = user.getProcessStatus();
		this.netStatus = user.getNetStatus();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getGamePoints() {
		return gamePoints;
	}
	public void setGamePoints(int gamePoints) {
		this.gamePoints = gamePoints;
	}
	public ProcessStatus getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(ProcessStatus processStatus) {
		this.processStatus = processStatus;
	}
	public NetStatus getNetStatus() {
		return netStatus;
	}
	public void setNetStatus(NetStatus netStatus) {
		this.netStatus = netStatus;
	}
	
	
}
