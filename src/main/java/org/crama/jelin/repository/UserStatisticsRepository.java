package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserStatistics;

public interface UserStatisticsRepository {

	List<UserStatistics> getAllUsersStatistics();
	
	List<UserStatistics> getUsersStatistics(List<User> users);
	
	void update(UserStatistics userStats);

}
