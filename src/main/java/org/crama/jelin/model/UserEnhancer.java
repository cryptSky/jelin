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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.crama.jelin.model.Constants.Status;

@Entity
@Table(name = "user_enhancer")
public class UserEnhancer implements Serializable {
	
	private static final long serialVersionUID = 5406665474312678784L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER) 
	@JoinColumn(name = "ENHANCER_ID", nullable = false)
	private Enhancer enhancer;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER) 
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@Column(name="STATUS", nullable = true)
	private Status status;
	
	@Column(name="RECEIVE_DATE", nullable = true)
	private Date receiveDate;

	public UserEnhancer() {}
	
	public UserEnhancer(Enhancer enhancer, User user, Status status, Date receiveDate) {
		super();
		this.enhancer = enhancer;
		this.user = user;
		this.status = status;
		this.receiveDate = receiveDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Enhancer getEnhancer() {
		return enhancer;
	}

	public void setEnhancer(Enhancer enhancer) {
		this.enhancer = enhancer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	
	
}
