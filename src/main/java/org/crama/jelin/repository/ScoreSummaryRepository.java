package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.ScoreSummary;
import org.crama.jelin.model.User;

public interface ScoreSummaryRepository {
	void saveSummary(ScoreSummary summary);
	void updateSummary(ScoreSummary summary);
	List<ScoreSummary> getSummaryByGame(Game game);
	ScoreSummary getSummaryByGameAndUser(Game game, User player);
}
