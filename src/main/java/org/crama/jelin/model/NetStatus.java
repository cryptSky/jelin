package org.crama.jelin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NetStatus")
public class NetStatus {
	
	public static final String OFFLINE = "OFFLINE";
	public static final String SHADOW = "SHADOW";
	public static final String ONLINE = "ONLINE";
		
	@Id
	@GeneratedValue
	@Column(name="ID", nullable=false)
	private int id;
	@Column(name="NET_STATUS", nullable=false, unique = true)
	private String status;
	
	public NetStatus() {
		
	}
	
	public NetStatus(String status) {
		super();
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
