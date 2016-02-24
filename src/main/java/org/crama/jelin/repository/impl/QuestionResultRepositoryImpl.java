package org.crama.jelin.repository.impl;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Answer;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.Question;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.QuestionResultRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("questionResultRepository")
public class QuestionResultRepositoryImpl implements QuestionResultRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionResult> getPersonalResults(Game game, User player) {
		GameRound round = game.getRound();
				
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(QuestionResult.class);
		Criterion plNum = Restrictions.eq("player", player);
		Criterion gr = Restrictions.eq("gameRound", round);
		criteria.add(Restrictions.and(plNum, gr));
		
		return criteria.list();
	}

	@Override
	@Transactional
	public void saveResult(QuestionResult result) {
		Session session = sessionFactory.getCurrentSession();	
		session.save(result);
		
	}

	@Override
	@Transactional
	public void update(QuestionResult result) {
		Session session = sessionFactory.getCurrentSession();	
		session.update(result);
		
	}

	@Override
	public long getWrongAnswerCount(Game game, User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(QuestionResult.class);
		criteria.add(Restrictions.eq("game", game))
			    .add(Restrictions.eq("player", user))
				.add(Restrictions.eq("score", 0));
		criteria.setProjection(Projections.rowCount());
		
		long result = (long) criteria.uniqueResult();
		
		return result;
	}
	
	@Override
	public long getCorrectAnswerCount(Game game, User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(QuestionResult.class);
		criteria.add(Restrictions.eq("game", game))
			    .add(Restrictions.eq("player", user))
				.add(Restrictions.gt("score", 0));
		criteria.setProjection(Projections.rowCount());
		
		long result = (long) criteria.uniqueResult();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionResult> getQuestionResultsByGame(Game game) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(QuestionResult.class);
		criteria.add(Restrictions.eq("game", game));
		
		return criteria.list();
	}
	
}
