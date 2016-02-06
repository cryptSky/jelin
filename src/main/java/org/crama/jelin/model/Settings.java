package org.crama.jelin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "settings")
public class Settings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "FULL_NAME", nullable = false)
	private String fullName;
	
	@Column(name = "SHORT_NAME", nullable = false)
	private String shortName;
	
	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	@Column(name = "URL", nullable = false)
	private String url;
	
	@Column(name = "INVITE_TIMEOUT", nullable = false)
	private String inviteTimeout;
	
	@Column(name = "NEXT_INVITE_ACTIVE_TIMEOUT", nullable = false)
	private String nextInviteActiveTimeout;
	
	@Column(name = "NEXT_INVITE_SHADOW_TIMEOUT", nullable = false)
	private String nextInviteShadowTimeout;
	
	@Column(name = "CATEGORY_TIMEOUT", nullable = false)
	private String categoryTimeout;
	
	@Column(name = "ROUND_NUMBER", nullable = false)
	private String roundNumber;
	
	@Column(name = "QUESTION_NUMBER", nullable = false)
	private String questionNumber;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "START_REGISTER_DATE", nullable = false)
	private Date startRegisterDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "END_REGISTER_DATE", nullable = false)
	private Date endRegisterDate;
	
	public Date getStartRegisterDate() {
		return startRegisterDate;
	}

	public void setStartRegisterDate(Date startRegisterDate) {
		this.startRegisterDate = startRegisterDate;
	}

	public Date getEndRegisterDate() {
		return endRegisterDate;
	}

	public void setEndRegisterDate(Date endRegisterDate) {
		this.endRegisterDate = endRegisterDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInviteTimeout() {
		return inviteTimeout;
	}

	public void setInviteTimeout(String inviteTimeout) {
		this.inviteTimeout = inviteTimeout;
	}

	public String getNextInviteActiveTimeout() {
		return nextInviteActiveTimeout;
	}

	public void setNextInviteActiveTimeout(String nextInviteActiveTimeout) {
		this.nextInviteActiveTimeout = nextInviteActiveTimeout;
	}

	public String getNextInviteShadowTimeout() {
		return nextInviteShadowTimeout;
	}

	public void setNextInviteShadowTimeout(String nextInviteShadowTimeout) {
		this.nextInviteShadowTimeout = nextInviteShadowTimeout;
	}

	public String getCategoryTimeout() {
		return categoryTimeout;
	}

	public void setCategoryTimeout(String categoryTimeout) {
		this.categoryTimeout = categoryTimeout;
	}

	public String getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(String roundNumber) {
		this.roundNumber = roundNumber;
	}

	public String getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}
	
	
	
	
}
