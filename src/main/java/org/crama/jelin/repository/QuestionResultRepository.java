package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.Question;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.User;

public interface QuestionResultRepository {
	List<QuestionResult> getPersonalResults(Game game, User player) throws GameException;
	List<User> getPlayersWithoutResult(GameRound round, Question question);
	
	void saveResult(QuestionResult result);
	void update(QuestionResult result);
	
}
