package org.crama.jelin.service;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.User;

public interface GameService {
	void startGame(Game game);

	Game getGameByPlayer(User player);

	void saveRoundCategory(Game game, Category categoryObj);
	
	
}
