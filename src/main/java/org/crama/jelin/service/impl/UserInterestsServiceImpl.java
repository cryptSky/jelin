package org.crama.jelin.service.impl;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.UserInterestsRepository;
import org.crama.jelin.service.UserInterestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userInterestsService")
public class UserInterestsServiceImpl implements UserInterestsService {

	@Autowired
	private UserInterestsRepository userInterestRepository;
	
	@Override
	public List<User> getUsersByTheme(Category theme) {
		return userInterestRepository.getUsersByTheme(theme.getId());
	}

	@Override
	public List<User> getUsersByThemeFromUsers(List<User> users, Category theme) {
		return userInterestRepository.getUsersByThemeFromUsers(users, theme.getId());
	}

	@Override
	public List<User> getUsersByThemeAndDifficulty(Category theme, Difficulty difficulty) {
		return userInterestRepository.getUsersByThemeAndDifficulty(theme.getId(), difficulty.getId());
	}

	@Override
	public List<User> getUsersByThemeAndDifficultyFromUsers(List<User> users, Category theme,
			Difficulty difficulty) {
		return userInterestRepository.getUsersByThemeAndDifficultyFromUsers(users, theme.getId(), difficulty.getId());
	}

}
