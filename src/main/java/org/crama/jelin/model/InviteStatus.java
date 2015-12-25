package org.crama.jelin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "InviteStatus")
public class InviteStatus implements Serializable {
	
	private static final long serialVersionUID = 7251119246318607890L;
	
	public static final String OPEN = "OPEN";
	public static final String ACCEPTED = "ACCEPTED";
	public static final String REJECTED = "REJECTED";
	public static final String EXPIRED = "EXPIRED";
	
	@Id
	@GeneratedValue
	@Column(name="STATUS_ID", nullable=false)
	private int statusId;
	@Column(name="STATUS", nullable=false, unique=true)
	private String status;
	
	public InviteStatus() {
		
	}
	
	public InviteStatus(String status) {
		super();
		this.status = status;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
