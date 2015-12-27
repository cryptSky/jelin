package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.User;
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
			+ "WHERE theme = :theme)";
	
	private static final String GET_USERS_BY_THEME_DIFF = "FROM User u "
			+ "WHERE u.id IN (SELECT user FROM UserInterests "
			+ "WHERE theme = :theme "
			+ "AND difficulty = :difficulty)";
	
	private static final String GET_USERS_BY_THEME_FROM_USERS = "FROM User u "
			+ "WHERE u.id IN (SELECT user FROM UserInterests "
			+ "WHERE user IN (:users) "
			+ "AND theme = :theme)";
	
	private static final String GET_USERS_BY_THEME_DIFF_USERS = "FROM User u "
			+ "WHERE u.id IN (SELECT user FROM UserInterests "
			+ "WHERE user IN (:users) "
			+ "AND theme = :theme "
			+ "AND difficulty = :difficulty)";
	
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
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USERS_BY_THEME_FROM_USERS);
		query.setParameterList("users", users);
		query.setParameter("themeID", themeID);
		List<User> usr = query.list();
	    	
		return usr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByThemeAndDifficultyFromUsers(List<User> users, Category theme, Difficulty difficulty) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USERS_BY_THEME_DIFF_USERS);
		query.setParameterList("users", users);
		query.setParameter("theme", theme);
		query.setParameter("difficulty", difficulty);
		List<User> usr = query.list();
	    		
		return usr;
	}

}
