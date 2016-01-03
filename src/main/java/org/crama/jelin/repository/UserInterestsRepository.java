package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.User;

public interface UserInterestsRepository {
	List<User> getUsersByTheme(int themeID);
	
	List<User> getUsersByThemeFromUsers(List<User> users, int themeID);
	
	List<User> getUsersByThemeAndDifficulty(int themeID, int difficultyID);
	
	List<User> getUsersByThemeAndDifficultyFromUsers(List<User> users, int themeID, int difficultyID);
	
	
}
