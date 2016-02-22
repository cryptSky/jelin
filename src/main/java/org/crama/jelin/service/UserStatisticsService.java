package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.ScoreSummary;
import org.crama.jelin.model.User;

public interface UserStatisticsService {

	void updateDaysInGame(User user);

	void buyGoldAcorns(User user, Integer acorns);

	void addInitiatedGame(User creator);

	void saveGameSummaryStats(List<ScoreSummary> summaries);

	void increaseSocialInvites(User user);

	void increaseSocialShares(User user);


}
