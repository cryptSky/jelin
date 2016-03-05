package org.crama.jelin.repository;

import java.util.Date;
import java.util.List;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserDailyStats;

public interface UserDailyStatsRepository {
	List<UserDailyStats> getRatingByTimeAndUsers(List<User> users, Date fromDate);
	List<UserDailyStats> getRatingByTime(Date fromDate);
	
	void update(UserDailyStats stats);
}
