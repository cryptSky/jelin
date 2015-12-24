package org.crama.jelin.service;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;

public interface GameService {
	boolean initGame(Category theme, boolean random);
	
	boolean updateDifficulty(int gameId, Difficulty difficulty);
}
