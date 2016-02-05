package org.crama.jelin.repository;

import org.crama.jelin.model.Constants.GameState;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameOpponent;
import org.crama.jelin.model.User;

public interface GameInitRepository {
	
	boolean saveGame(Game game);
	
	//boolean updateDifficulty(int gameId, Difficulty difficulty);

	Game getGame(User creator, GameState state);
	
	boolean updateGame(Game game);

	Game getInviteGame(User user);

	GameOpponent getGameOpponent(Game game, User opponent);
	
	void clearSession();

	boolean removeGameOpponent(Game game, User user);
	
	long getExpiredInvites(User user);
		
}
