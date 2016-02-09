package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Answer;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.Question;
import org.crama.jelin.model.User;

public interface AnswerRepository {
	void saveAnswer(Answer answer);
	List<Answer> getRoundAnswers(GameRound round);
	List<Answer> getRoundAnswersByQuestion(GameRound round, Question question);
	List<Answer> getAnswersByGame(Game game);
	void update(Answer answer);
}
