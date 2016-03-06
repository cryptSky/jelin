package org.crama.jelin.repository.impl;

import java.util.List;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserStatistics;
import org.crama.jelin.repository.UserStatisticsRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userStatisticsRepository")
public class UserStatisticsRepositoryImpl implements UserStatisticsRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private static final String GET_ALL_STATISTICS = "FROM UserStatistics "
													+ "ORDER BY points DESC ";
	private static final String GET_USERS_STATISTICS = "FROM UserStatistics us WHERE us.user IN (:users) "
			+ "ORDER BY points DESC ";
													
	
	@Override
	public List<UserStatistics> getAllUsersStatistics() {
		
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_STATISTICS);
		@SuppressWarnings("unchecked")
		List<UserStatistics> stats = (List<UserStatistics>)query.list();
		return stats;
		
	}


	@Override
	public List<UserStatistics> getUsersStatistics(List<User> users) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USERS_STATISTICS);
		query.setParameterList("users", users);
		@SuppressWarnings("unchecked")
		List<UserStatistics> stats = (List<UserStatistics>)query.list();
		return stats;
	}

}
