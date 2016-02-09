package org.crama.jelin.repository;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.Constants.Readiness;

public interface GameRepository {
	void updateGame(Game game);

	Readiness getReadiness(Game game); // get readiness from DB
	
	void cleanUpGame(Game game);
	
	Game reloadGame(Game game);
}
