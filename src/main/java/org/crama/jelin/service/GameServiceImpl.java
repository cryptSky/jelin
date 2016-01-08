package org.crama.jelin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameOpponent;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.GameRepository;
import org.crama.jelin.repository.GameRoundRepository;
import org.crama.jelin.repository.UserRepository;
import org.crama.jelin.model.Constants.ProcessStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("gameService")
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GameRoundRepository gameRoundRepository;
	
	private ArrayList<GameRound> gameRounds;
	
	@Override
	@Transactional
	public void startGame(Game game) {
		ArrayList<User> hosts = setUpHosts(game);
		
		gameRounds = new ArrayList<GameRound>();
		for (int round = 0; round < 4; round++)
		{
			GameRound gameRound = new GameRound(game, round, hosts.get(round));
			gameRounds.add(gameRound);
		}
		
		gameRoundRepository.saveOrUpdateRounds(gameRounds);
		
		game.setHost(hosts.get(0));
		game.setRound(gameRounds.get(0));
		gameRepository.updateGame(game);
						
		User creator = game.getCreator();
		creator.setProcessStatus(ProcessStatus.INGAME);
		userRepository.updateUser(creator);
		
		for (GameOpponent opponent: game.getGameOpponents())
		{
			User player = opponent.getUser();
			player.setProcessStatus(ProcessStatus.INGAME);
			userRepository.updateUser(player);
		}
		
	}

	private ArrayList<User> setUpHosts(Game game)
	{
		int playersCount = game.getGameOpponents().size() + 1;
		ArrayList<User> hostOrder = new ArrayList<User>();
		hostOrder.add(game.getCreator());
		for (GameOpponent opponent: game.getGameOpponents())
		{
			hostOrder.add(opponent.getUser());
		}
		
		if (playersCount == 3)
		{
			Random r = new Random();
			hostOrder.add(hostOrder.get(r.nextInt(3)));
		}
		
		Collections.shuffle(hostOrder);
						
		return hostOrder;		
	}

}
