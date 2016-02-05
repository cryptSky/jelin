package org.crama.jelin.repository.impl;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.GameBot;
import org.crama.jelin.repository.GameBotRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("gameBotRepository")
public class GameBotRepositoryImpl implements GameBotRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GameBot> getBotByThemeAndDifficulty(Category theme, Difficulty diff) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GameBot.class);
		Criterion category = Restrictions.eq("category", theme);
		Criterion difficulty = Restrictions.eq("difficulty", diff);
		criteria.add(Restrictions.and(category, difficulty));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GameBot> getBotByNullThemeAndDifficulty(Difficulty diff) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GameBot.class);
		Criterion category = Restrictions.isNull("category");
		Criterion difficulty = Restrictions.eq("difficulty", diff);
		criteria.add(Restrictions.and(category, difficulty));
		
		return criteria.list();
	}

}
