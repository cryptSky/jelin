package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserSession;
import org.crama.jelin.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userSessionService")
public class UserSessionServiceImpl implements UserSessionService {

	@Autowired
	private UserSessionRepository userSessionRepository;
	
	@Override
	public UserSession getSession(User user) {
		UserSession s = userSessionRepository.getSession(user);
		return s;
	}

	@Override
	public void saveOrUpdate(UserSession userSession) {
		userSessionRepository.saveOrUpdate(userSession);
		
	}

	@Override
	public List<UserSession> getAllSessions(User user) {
		return userSessionRepository.getAllSessions(user);
	}

}
