package org.crama.jelin.repository;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.Constants.Readiness;

public interface GameRepository {
	void updateGame(Game game);

	Readiness getReadiness(Game game); // get readiness from DB
	void setReadiness(Game game, Readiness readiness); // set readiness and flush
	
	void cleanUpGame(Game game);
	
	Game reloadGame(Game game);

	void lock(Game game);

	Game getGameById(int gameId);
}
