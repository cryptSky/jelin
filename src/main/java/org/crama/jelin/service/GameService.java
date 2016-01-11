package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameBot;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.Question;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.ScoreSummary;
import org.crama.jelin.model.User;

public interface GameService {
	void startGame(Game game);

	void processAnswer(Game game, User player, int variant, int time);
	
	void processBotsAnswers(Game game);
	
	boolean nextRound(Game game);
	
	void finishRound(GameRound round);
	
	void finishGame(Game game);
	
	void updateGame(Game game);
	
	List<ScoreSummary> getScoreSummary(Game game);
	
	List<QuestionResult> getPersonalRoundResults(Game game, User player) throws GameException;
	
	Question getNextQuestion(Game game);
	
	Game getGameByPlayer(User player);

	void saveRoundCategory(Game game, Category categoryObj);
	
}
