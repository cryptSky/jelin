package org.crama.jelin.repository;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;

public interface GameRepository {
	
	boolean initGame(Category theme, boolean random);
	
	boolean updateDifficulty(int gameId, Difficulty difficulty);
}
