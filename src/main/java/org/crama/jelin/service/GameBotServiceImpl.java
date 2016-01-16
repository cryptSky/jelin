package org.crama.jelin.service;

import java.util.List;
import java.util.Random;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.GameBot;
import org.crama.jelin.model.Question;
import org.crama.jelin.repository.GameBotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gameBotService")
public class GameBotServiceImpl implements GameBotService {

	@Autowired
	private GameBotRepository gameBotRepository;
	
	@Override
	public GameBot getRandomBot(Category theme, Difficulty diff) throws GameException {
		List<GameBot> bots = gameBotRepository.getBotByThemeAndDifficulty(theme, diff);
		
		if (bots.size() == 0)
		{
			bots = gameBotRepository.getBotByNullThemeAndDifficulty(diff);
		}
		if (bots.size() == 0)
		{
			throw new GameException(0, "There is no bot available to play with.");
		}
				
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(bots.size());
		GameBot bot = bots.get(index);
		
		return bot;
	}
	
	@Override
	public int getBotChoice(GameBot bot, Question question) {
		double botProbability = bot.getAnswerProbability(); 
		Random random = new Random();
		double randomProb = random.nextDouble();
		
		int answer = -1;
		if (randomProb <= botProbability)
		{
			answer = question.getAnswer();
		}
		else
		{
			answer = random.nextInt(4);
			while(answer + 1 == question.getAnswer())
			{
				answer = random.nextInt(4);
			}
			
			answer++;
		}
		
		return answer;
	}

	@Override
	public int getBotTime(GameBot bot, Question question) {
		int ta = question.getTime();
		int tb = question.getTimeB();
		int tc = question.getTimeC();
		
		double k = bot.getReadingSpeed();
		
		int result = (int)(tb + (ta - tb - tc)*k);
		
		return result;
	}

}
