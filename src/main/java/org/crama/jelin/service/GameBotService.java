package org.crama.jelin.service;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.GameBot;
import org.crama.jelin.model.Question;

public interface GameBotService {
	GameBot getRandomBot(Category theme, Difficulty diff) throws GameException;
	int getBotChoice(GameBot bot, Question question);
	int getBotTime(GameBot bot, Question question);	
}
