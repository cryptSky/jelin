package org.crama.jelin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameOpponent;
import org.crama.jelin.model.User;
import org.crama.jelin.model.Constants.ProcessStatus;
import org.springframework.stereotype.Service;

@Service("gameService")
public class GameServiceImpl implements GameService {

	@Override
	public boolean startGame(Game game) {
		ArrayList<Integer> hostOrder = setUpHosts(game);
		game.setHostOrder(hostOrder);
		
		game.setHost(game.getHostOrder().get(0));
		game.setRound(0);
		
		User creator = game.getCreator();
		creator.setProcessStatus(ProcessStatus.INGAME);
		
		for (GameOpponent opponent: game.getGameOpponents())
		{
			User player = opponent.getUser();
			player.setProcessStatus(ProcessStatus.INGAME);
		}
		
		return true;
		
	}

	private ArrayList<Integer> setUpHosts(Game game)
	{
		int playersCount = game.getGameOpponents().size() + 1;
		ArrayList<Integer> hostOrder = new ArrayList<Integer>();
		hostOrder.add(0);
		hostOrder.add(1);
		hostOrder.add(2);
		if (playersCount == 4)
		{
			hostOrder.add(3);
		}
		else
		{
			Random r = new Random();
			hostOrder.add(r.nextInt(3));
		}
		
		Collections.shuffle(hostOrder);
		return hostOrder;
		
	}

	@Override
	public boolean nextRound(Game game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean finishGame(Game game) {
		// TODO Auto-generated method stub
		return false;
	}
}
