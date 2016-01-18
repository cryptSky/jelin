package org.crama.jelin.service;

import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.Question;

public interface PointsCalculatorService {
	void calculate(GameRound round);
	void calculateQuestion(GameRound round, Question question);
}
