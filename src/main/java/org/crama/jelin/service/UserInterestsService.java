package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.User;

public interface UserInterestsService {
	List<User> getUsersByTheme(Category theme);
	
	List<User> getUsersByThemeFromUsers(List<User> users, Category theme);
	
	List<User> getUsersByThemeAndDifficulty(Category theme, Difficulty difficulty);
	
	List<User> getUsersByThemeAndDifficultyFromUsers(List<User> users, Category theme, Difficulty difficulty);
}
