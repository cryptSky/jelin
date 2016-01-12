package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.ScoreSummary;

public interface ScoreSummaryRepository {
	void saveSummary(ScoreSummary summary);
	List<ScoreSummary> getSummaryByGame(Game game);
}
