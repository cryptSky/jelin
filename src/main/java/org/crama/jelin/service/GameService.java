package org.crama.jelin.service;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.User;

public interface GameService {
	boolean initGame(User creator, Category theme, boolean random);
	
	boolean updateDifficulty(Game game, Difficulty difficulty);

	Game getCreatedGame(User creator);
}
