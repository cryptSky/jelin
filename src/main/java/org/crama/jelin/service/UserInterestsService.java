package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.User;

public interface UserInterestsService {
	List<Integer> getUserIdsByTheme(Category theme);
	
	List<Integer> getUserIdsByThemeFromUsers(List<Integer> users, Category theme);
	
	List<Integer> getUserIdsByThemeAndDifficulty(Category theme, Difficulty difficulty);
	
	List<Integer> getUserIdsByThemeAndDifficultyFromUsers(List<Integer> users, Category theme, Difficulty difficulty);
}
