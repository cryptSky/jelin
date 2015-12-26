package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserInterests;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userInterestsRepository")
public class UserInterestsRepositoryImpl implements UserInterestsRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getUserIdsByTheme(int themeID) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserInterests.class);
		criteria.add(Restrictions.eq("category_id", themeID))
				.addOrder(Order.desc("games_played"))
				.setProjection(Projections.id());
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getUserIdsByThemeAndDifficulty(int themeID, int difficultyID) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserInterests.class);
		criteria.add(Restrictions.eq("category_id", themeID))
				.add(Restrictions.eq("difficulty_id", difficultyID))
				.addOrder(Order.desc("games_played"))
				.setProjection(Projections.id());
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getUserIdsByThemeFromUsers(List<Integer> users, int themeID) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserInterests.class);
		criteria.add(Restrictions.in("user_id", users))
				.add(Restrictions.eq("category_id", themeID))
				.addOrder(Order.desc("games_played"))
				.setProjection(Projections.id());
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getUserIdsByThemeAndDifficultyFromUsers(List<Integer> users, int themeID, int difficultyID) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserInterests.class);
		criteria.add(Restrictions.in("user_id", users))
				.add(Restrictions.eq("category_id", themeID))
				.add(Restrictions.eq("difficulty_id", difficultyID))
				.addOrder(Order.desc("games_played"))
				.setProjection(Projections.id());
		
		return criteria.list();
	}

}
