package org.crama.jelin.service.impl;

import java.util.Date;

import org.crama.jelin.model.Constants.InviteStatus;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserActivity;
import org.crama.jelin.repository.UserRepository;
import org.crama.jelin.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userActivityService")
public class UserActivityServiceImpl implements UserActivityService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void saveUserLoginActivity(User user) {
		
		UserActivity userActivity = user.getUserActivity();
		userActivity.setLastLogin(new Date());
		user.setUserActivity(userActivity);
		userRepository.updateUser(user);
		
	}

	@Override
	public void saveUserGameActivity(User user) {
		
		UserActivity userActivity = user.getUserActivity();
		userActivity.setLastGame(new Date());
		user.setUserActivity(userActivity);
		userRepository.updateUser(user);
		
	}

	@Override
	public void saveInvite(User user, InviteStatus inviteStatus) {
		
		UserActivity userActivity = user.getUserActivity();
		userActivity.setLastInvite(new Date());
		userActivity.setLastInviteResponse(inviteStatus);
		user.setUserActivity(userActivity);
		userRepository.updateUser(user);
		
	}

	@Override
	public void saveUserOnlineActivity(User user) {

		UserActivity userActivity = user.getUserActivity();
		userActivity.setLastTimeOnline(new Date());
		user.setUserActivity(userActivity);
		userRepository.updateUser(user);
		
	}
}
