package org.crama.jelin.service;

import java.util.HashSet;
import java.util.Set;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameState;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.GameInitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gameInitService")
public class GameInitServiceImpl implements GameInitService {

	@Autowired
	private GameInitRepository gameInitRepository;
	
	@Override
	public boolean initGame(User creator, Category theme, boolean random) {
		
		Game game = new Game(theme, random);
		GameState state = gameInitRepository.getGameState(GameState.CREATED);
		game.setGameState(state);
		//Set<GameUser> gameUserSet = new HashSet<GameUser>();
		//GameUser gameUser = new GameUser(creator, game, true);
		//gameUserSet.add(gameUser);
		//game.setGamePlayerSet(gameUserSet);
		
		game.setCreator(creator);
		
		return gameInitRepository.saveGame(game);
	}

	@Override
	public boolean updateDifficulty(Game game, Difficulty difficulty) {
		game.setDifficulty(difficulty);
		return gameInitRepository.updateGame(game);
	}

	@Override
	public Game getCreatedGame(User creator) {
		Game game = gameInitRepository.getCreatedGame(creator);
		return game;
	}

	@Override
	public void removeOpponent(Game game, int userId) {
		Set<User> opponents = game.getGameOpponents();
		for (User user:new HashSet<User>(opponents)) {
			if (user.getId() == userId) {
				opponents.remove(user);
				gameInitRepository.updateGame(game);
			}
		}
	}

}
