package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.User;

public interface QuestionResultRepository {
	List<QuestionResult> getPersonalResults(Game game, User player) throws GameException;
	long getWrongAnswerCount(Game game, User user);
		
	void saveResult(QuestionResult result);
	void update(QuestionResult result);
	
	List<QuestionResult> getQuestionResultsByGame(Game game);
}
