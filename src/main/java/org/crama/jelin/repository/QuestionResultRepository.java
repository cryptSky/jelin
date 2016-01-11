package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.User;

public interface QuestionResultRepository {
	List<QuestionResult> getPersonalRoundResults(Game game, User player) throws GameException;
	void saveResult(QuestionResult result);
}
