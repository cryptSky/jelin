package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.ScoreSummary;
import org.crama.jelin.model.User;
import org.crama.jelin.model.json.RatingJson;

public interface UserStatisticsService {

	List<RatingJson> getRating(User user, int time, int people);

	void updateDaysInGame(User user);

	void buyGoldAcorns(User user, Integer acorns);

	void addInitiatedGame(User creator);

	void saveGameSummaryStats(List<ScoreSummary> summaries);

	void increaseSocialInvites(User user);

	void increaseSocialShares(User user);


}
