package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserSession;

public interface UserSessionService {

	UserSession getSession(User user);
	void saveOrUpdate(UserSession userSession);
	
	List<UserSession> getAllSessions(User user);
}
