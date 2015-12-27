package org.crama.jelin.repository;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameOpponent;
import org.crama.jelin.model.User;

public interface GameInitRepository {
	
	boolean saveGame(Game game);
	
	//boolean updateDifficulty(int gameId, Difficulty difficulty);

	Game getCreatedGame(User creator);

	boolean updateGame(Game game);

	Game getInviteGame(User user);

	GameOpponent getGameOpponent(Game game, User opponent);
	
	void clearSession();

	void removeGameOpponent(Game game, User user);
}
