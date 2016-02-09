package org.crama.jelin.service;

import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.Question;

public interface PointsCalculatorService {
	void calculateQuestion(GameRound round, Question question);
}
