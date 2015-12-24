package org.crama.jelin.service;

import java.util.HashSet;
import java.util.Set;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameState;
import org.crama.jelin.model.GameUser;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gameService")
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository gameRepository;
	
	@Override
	public boolean initGame(User creator, Category theme, boolean random) {
		
		Game game = new Game(theme, random);
		GameState state = new GameState(GameState.CREATED);
		game.setGameState(state);
		Set<GameUser> gameUserSet = new HashSet<GameUser>();
		GameUser gameUser = new GameUser(creator, game, true);
		gameUserSet.add(gameUser);
		
		game.setGamePlayerSet(gameUserSet);
		
		return gameRepository.saveGame(game);
	}

	@Override
	public boolean updateDifficulty(Game game, Difficulty difficulty) {
		game.setDifficulty(difficulty);
		return gameRepository.updateGame(game);
	}

	@Override
	public Game getCreatedGame(User creator) {
		Game game = gameRepository.getCreatedGame(creator);
		return game;
	}

}
