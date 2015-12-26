package org.crama.jelin.repository;

import java.util.List;

public interface UserInterestsRepository {
	List<Integer> getUserIdsByTheme(int themeID);
	
	List<Integer> getUserIdsByThemeFromUsers(List<Integer> users, int themeID);
	
	List<Integer> getUserIdsByThemeAndDifficulty(int themeID, int difficultyID);
	
	List<Integer> getUserIdsByThemeAndDifficultyFromUsers(List<Integer> users, int themeID, int difficultyID);
	
	
}
