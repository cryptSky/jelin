package org.crama.jelin.service;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.GameBot;

public interface GameBotService {
	GameBot getRandomBot(Category theme, Difficulty diff);
}
