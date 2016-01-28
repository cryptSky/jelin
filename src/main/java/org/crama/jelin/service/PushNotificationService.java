package org.crama.jelin.service;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.User;

public interface PushNotificationService {
	
	void sendPushInviteRandom(User user, User initiator, Category theme);
	void sendPushInviteFriend(User user, User initiator, Category theme);
	void sendPushInviteFriends(User user, User initiator, Category theme, Game game);
	void sendPushMissedGames(User user);
	
}
