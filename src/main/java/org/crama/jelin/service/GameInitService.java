package org.crama.jelin.service;

import java.util.Set;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Constants.InviteStatus;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.User;

public interface GameInitService {
	boolean initGame(User creator, Category theme, boolean random);
	
	boolean updateDifficulty(Game game, Difficulty difficulty);

	Game getCreatedGame(User creator);

	void removeOpponent(Game game, int userId);

	Set<User> getGameOpponents(Game game);

	InviteStatus inviteUser(Game game, User creator, User opponent);

	Game getInviteGame(User user);

	void confirmInvite(Game game, User user);

	void refuseInvite(Game game, User user);

	boolean checkInviteStatus(Game game);

}
