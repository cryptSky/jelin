package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.GameBot;

public interface GameBotRepository {
	List<GameBot> getBotByThemeAndDifficulty(Category theme, Difficulty diff);
	List<GameBot> getBotByNullThemeAndDifficulty(Difficulty diff);
}
