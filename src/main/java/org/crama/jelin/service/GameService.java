package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.Question;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.ScoreSummary;
import org.crama.jelin.model.User;

public interface GameService {
	void startGame(Game game) throws GameException;
	
	Question processQuestion(Game game, User user) throws GameException;
	
	List<QuestionResult> processResult(Game game, User player) throws GameException;
	
	void processAnswer(Game game, User player, int variant, int time);
	
	void setRandomCategory(Game game) throws GameException;
	
	void saveRoundCategory(Game game, Category categoryObj) throws GameException;
	
	List<ScoreSummary> getScoreSummary(Game game, User player);
	
	List<QuestionResult> getPersonalResults(Game game, User player) throws GameException;
		
	Game getGameByPlayer(User player);
	
	void updateGame(Game game);
	
	void finishGame(Game game);
		
}
