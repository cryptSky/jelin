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

import org.crama.jelin.model.Constants.Readiness;

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
	
	@Column(name = "EMAIL_PASSWORD", nullable = false)
	private String emailPassword;
	
	@Column(name = "URL", nullable = false)
	private String url;
	
	@Column(name = "INVITE_TIMEOUT", nullable = false)
	private int inviteTimeout;
	
	@Column(name = "INVITE_CHECK_TIMEOUT", nullable = false)
	private int inviteCheckTimeout;
	
	@Column(name = "NEXT_INVITE_ACTIVE_TIMEOUT", nullable = false)
	private int nextInviteActiveTimeout;
	
	@Column(name = "NEXT_INVITE_SHADOW_TIMEOUT", nullable = false)
	private int nextInviteShadowTimeout;
	
	@Column(name = "CATEGORY_TIMEOUT", nullable = false)
	private int categoryTimeout;
	
	@Column(name = "ROUND_NUMBER", nullable = false)
	private int roundNumber;
	
	@Column(name = "QUESTION_NUMBER", nullable = false)
	private int questionNumber;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "START_REGISTER_DATE", nullable = false)
	private Date startRegisterDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "END_REGISTER_DATE", nullable = false)
	private Date endRegisterDate;
	
	@Column(name = "CATEGORY_OFFLINE_TIMEOUT", nullable = false)
	private int categoryOfflineTimeout;
	
	@Column(name = "QUESTION_OFFLINE_TIMEOUT", nullable = false)
	private int questionOfflineTimeout;
	
	@Column(name = "ANSWER_OFFLINE_TIMEOUT", nullable = false)
	private int answerOfflineTimeout;
	
	@Column(name = "RESULT_OFFLINE_TIMEOUT", nullable = false)
	private int resultOfflineTimeout;
	
	@Column(name = "SUMMARY_OFFLINE_TIMEOUT", nullable = false)
	private int summaryOfflineTimeout;
	
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
	
	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getInviteTimeout() {
		return inviteTimeout;
	}

	public void setInviteTimeout(int inviteTimeout) {
		this.inviteTimeout = inviteTimeout;
	}

	public int getInviteCheckTimeout() {
		return inviteCheckTimeout;
	}

	public void setInviteCheckTimeout(int inviteCheckTimeout) {
		this.inviteCheckTimeout = inviteCheckTimeout;
	}

	public int getNextInviteActiveTimeout() {
		return nextInviteActiveTimeout;
	}

	public void setNextInviteActiveTimeout(int nextInviteActiveTimeout) {
		this.nextInviteActiveTimeout = nextInviteActiveTimeout;
	}

	public int getNextInviteShadowTimeout() {
		return nextInviteShadowTimeout;
	}

	public void setNextInviteShadowTimeout(int nextInviteShadowTimeout) {
		this.nextInviteShadowTimeout = nextInviteShadowTimeout;
	}

	public int getCategoryTimeout() {
		return categoryTimeout;
	}

	public void setCategoryTimeout(int categoryTimeout) {
		this.categoryTimeout = categoryTimeout;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public int getCategoryOfflineTimeout() {
		return categoryOfflineTimeout;
	}

	public void setCategoryOfflineTimeout(int categoryOfflineTimeout) {
		this.categoryOfflineTimeout = categoryOfflineTimeout;
	}

	public int getQuestionOfflineTimeout() {
		return questionOfflineTimeout;
	}

	public void setQuestionOfflineTimeout(int questionOfflineTimeout) {
		this.questionOfflineTimeout = questionOfflineTimeout;
	}

	public int getAnswerOfflineTimeout() {
		return answerOfflineTimeout;
	}

	public void setAnswerOfflineTimeout(int answerOfflineTimeout) {
		this.answerOfflineTimeout = answerOfflineTimeout;
	}

	public int getResultOfflineTimeout() {
		return resultOfflineTimeout;
	}

	public void setResultOfflineTimeout(int resultOfflineTimeout) {
		this.resultOfflineTimeout = resultOfflineTimeout;
	}

	public int getSummaryOfflineTimeout() {
		return summaryOfflineTimeout;
	}

	public void setSummaryOfflineTimeout(int summaryOfflineTimeout) {
		this.summaryOfflineTimeout = summaryOfflineTimeout;
	}
	
	public int getOfflineTimeout(Readiness readiness)
	{
		int result = 30;
		switch(readiness)
		{
			case CATEGORY: result = getCategoryOfflineTimeout();
									break;
			case QUESTION: result = getQuestionOfflineTimeout();
									break;
			case ANSWER: result = getAnswerOfflineTimeout();
								  	break;
			case RESULT: result = getResultOfflineTimeout();
									break;
			case SUMMARY: result = getSummaryOfflineTimeout();
									break;
			default: break;
		}
		
		return result;
	}

}
