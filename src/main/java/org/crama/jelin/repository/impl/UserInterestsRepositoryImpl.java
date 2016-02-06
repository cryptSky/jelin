package org.crama.jelin.repository.impl;

import java.util.List;

import org.crama.jelin.model.User;
import org.crama.jelin.repository.UserInterestsRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userInterestsRepository")
public class UserInterestsRepositoryImpl implements UserInterestsRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final String GET_USERS_BY_THEME = "FROM User u "
			+ "WHERE u.id IN (SELECT user FROM UserInterests "
			+ "WHERE theme.id = :themeID)";
	
	private static final String GET_USERS_BY_THEME_DIFF = "FROM User u "
			+ "WHERE u.id IN (SELECT user FROM UserInterests "
			+ "WHERE theme.id = :themeID "
			+ "AND difficulty.id = :difficultyID)";
	
	private static final String GET_USERS_BY_THEME_FROM_USERS = "FROM User u "
			+ "WHERE u.id IN (SELECT user FROM UserInterests "
			+ "WHERE user IN (:users) "
			+ "AND theme.id = :themeID)";
	
	private static final String GET_USERS_BY_THEME_DIFF_USERS = "FROM User u "
			+ "WHERE u.id IN (SELECT user FROM UserInterests "
			+ "WHERE user IN (:users) "
			+ "AND theme.id = :themeID "
			+ "AND difficulty.id = :difficultyID)";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByTheme(int themeID) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USERS_BY_THEME);
		query.setParameter("themeID", themeID);
		List<User> usr = query.list();
	    	
		return usr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByThemeAndDifficulty(int themeID, int difficultyID) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USERS_BY_THEME_DIFF);
		query.setParameter("themeID", themeID);
		query.setParameter("difficultyID", difficultyID);
		List<User> usr = query.list();
	    	
		return usr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByThemeFromUsers(List<User> users, int themeID) {
		if (users.size() == 0)
		{
			return users;
		}
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USERS_BY_THEME_FROM_USERS);
		query.setParameterList("users", users);
		query.setParameter("themeID", themeID);
		List<User> usr = query.list();
	    	
		return usr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByThemeAndDifficultyFromUsers(List<User> users, int themeID, int difficultyID) {
		if (users.size() == 0)
		{
			return users;
		}
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USERS_BY_THEME_DIFF_USERS);
		query.setParameterList("users", users);
		query.setParameter("themeID", themeID);
		query.setParameter("difficultyID", difficultyID);
		List<User> usr = query.list();
	    		
		return usr;
	}

}
