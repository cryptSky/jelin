package org.crama.jelin.service;

import org.crama.jelin.model.Constants.InviteStatus;
import org.crama.jelin.model.User;

public interface UserActivityService {
	
	void saveUserLoginActivity(User user);
	
	void saveUserOnlineActivity(User user);

	void saveUserGameActivity(User creator);

	void saveInvite(User opponent, InviteStatus inviteStatus);

	
}
