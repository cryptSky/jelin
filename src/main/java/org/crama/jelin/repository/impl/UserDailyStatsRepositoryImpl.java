package org.crama.jelin.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserDailyStats;
import org.crama.jelin.repository.UserDailyStatsRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDailyStatsRepository")
public class UserDailyStatsRepositoryImpl implements UserDailyStatsRepository {

	private static final String GET_STATS_FROM_DATE_USERS = "FROM UserDailyStats u "
			+ "WHERE u.user IN (:users) "
			+ "AND u.date >= (:fromDate) ORDER BY u.user";
	
	private static final String GET_STATS_FROM_DATE = "FROM UserDailyStats u "
			+ "WHERE u.date >= (:fromDate) ORDER BY u.user";
		
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserDailyStats> getRatingByTimeAndUsers(List<User> users, Date fromDate) {
		if (users.size() == 0)
		{
			return getRatingByTime(fromDate);
		}
		
		Query query = sessionFactory.getCurrentSession().createQuery(GET_STATS_FROM_DATE_USERS);
		query.setParameterList("users", users);
		query.setParameter("fromDate", fromDate);
		List<UserDailyStats> usr = query.list();
	    		
		return usr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDailyStats> getRatingByTime(Date fromDate) {
			
		Query query = sessionFactory.getCurrentSession().createQuery(GET_STATS_FROM_DATE);
		query.setParameter("fromDate", fromDate);
		List<UserDailyStats> usr = query.list();
			    		
		return usr;
	}

}
