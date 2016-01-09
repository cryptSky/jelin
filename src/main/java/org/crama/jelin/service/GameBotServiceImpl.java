package org.crama.jelin.service;

import java.util.List;
import java.util.Random;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.GameBot;
import org.crama.jelin.repository.GameBotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gameBotService")
public class GameBotServiceImpl implements GameBotService {

	@Autowired
	private GameBotRepository gameBotRepository;
	
	@Override
	public GameBot getRandomBot(Category theme, Difficulty diff) {
		List<GameBot> bots = gameBotRepository.getBotByThemeAndDifficulty(theme, diff);
		
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(bots.size());
		GameBot bot = bots.get(index);
		
		return bot;
	}

}
