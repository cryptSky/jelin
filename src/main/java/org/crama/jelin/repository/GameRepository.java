package org.crama.jelin.repository;

import org.crama.jelin.model.Game;

public interface GameRepository {
	int nextRound(int roundID);
	void updateGame(Game game);
}
