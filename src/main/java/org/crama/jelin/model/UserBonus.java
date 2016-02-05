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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.crama.jelin.model.Constants.Status;

@Entity
@Table(name = "user_bonus")
public class UserBonus implements Serializable {
	
	private static final long serialVersionUID = 8802357689217829775L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY) 
	@JoinColumn(name = "BONUS_ID", nullable = false)
	private Bonus bonus;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY) 
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@Column(name="STATUS", nullable = true)
	private Status status;
	
	@Temporal(TemporalType.DATE)
	@Column(name="RECEIVE_DATE", nullable = true)
	private Date receiveDate;

	public UserBonus() {
		
	}
	
	
	public UserBonus(Bonus bonus, User user, Status status, Date receiveDate) {
		super();
		this.bonus = bonus;
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

	
	public Bonus getBonus() {
		return bonus;
	}

	public void setBonus(Bonus bonus) {
		this.bonus = bonus;
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
