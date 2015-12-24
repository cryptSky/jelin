package org.crama.jelin.service;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gameService")
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository gameRepository;
	
	@Override
	public boolean initGame(Category theme, boolean random) {
		return gameRepository.initGame(theme, random);
	}

	@Override
	public boolean updateDifficulty(int gameId, Difficulty difficulty) {
		return gameRepository.updateDifficulty(gameId, difficulty);
	}

}
