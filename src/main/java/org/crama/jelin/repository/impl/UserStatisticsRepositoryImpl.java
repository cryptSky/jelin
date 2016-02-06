package org.crama.jelin.repository.impl;

import java.util.List;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserStatistics;
import org.crama.jelin.repository.UserStatisticsRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userStatisticsRepository")
public class UserStatisticsRepositoryImpl implements UserStatisticsRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private static final String GET_ALL_STATISTICS = "FROM UserStatistics "
													+ "ORDER BY points DESC ";
													
	
	@Override
	public List<UserStatistics> getAllUsersStatistics() {
		
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_STATISTICS);
		query.setMaxResults(10);
		@SuppressWarnings("unchecked")
		List<UserStatistics> stats = (List<UserStatistics>)query.list();
		return stats;
		
	}
	
	
	
}
