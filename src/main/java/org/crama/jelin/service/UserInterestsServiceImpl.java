package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.UserInterestsRepository;
import org.crama.jelin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userInterestsService")
public class UserInterestsServiceImpl implements UserInterestsService {

	@Autowired
	private UserInterestsRepository userInterestRepository;
	
	@Override
	public List<Integer> getUserIdsByTheme(Category theme) {
		return userInterestRepository.getUserIdsByTheme(theme.getId());
	}

	@Override
	public List<Integer> getUserIdsByThemeFromUsers(List<Integer> users, Category theme) {
		return userInterestRepository.getUserIdsByThemeFromUsers(users, theme.getId());
	}

	@Override
	public List<Integer> getUserIdsByThemeAndDifficulty(Category theme, Difficulty difficulty) {
		return userInterestRepository.getUserIdsByThemeAndDifficulty(theme.getId(), difficulty.getId());
	}

	@Override
	public List<Integer> getUserIdsByThemeAndDifficultyFromUsers(List<Integer> users, Category theme,
			Difficulty difficulty) {
		return userInterestRepository.getUserIdsByThemeAndDifficultyFromUsers(users, theme.getId(), difficulty.getId());
	}

}
