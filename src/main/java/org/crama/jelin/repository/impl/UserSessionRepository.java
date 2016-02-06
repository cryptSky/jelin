package org.crama.jelin.repository.impl;

import java.util.List;

import org.crama.jelin.model.User;

import org.crama.jelin.model.UserSession;

public interface UserSessionRepository {
	UserSession getSession(User user);
	void saveOrUpdate(UserSession session);
	
	List<UserSession> getAllSessions(User user);
}
