package org.crama.jelin.repository;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameState;
import org.crama.jelin.model.User;

public interface GameInitRepository {
	
	boolean saveGame(Game game);
	
	//boolean updateDifficulty(int gameId, Difficulty difficulty);

	Game getCreatedGame(User creator);

	boolean updateGame(Game game);

	GameState getGameState(String state);
	
}
