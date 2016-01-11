package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.User;

public interface PointsCalculatorService {
	void calculate(GameRound round);
}
