package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
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
	public List<QuestionResult> getPersonalResults(Game game, User player) throws GameException {
		GameRound round = game.getRound();
		int playerNumber = game.getPlayerNumberByUser(player);
		if (playerNumber < 1 || playerNumber > 4)
		{
			throw new GameException(0, "Invalid player number!");
		}
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(QuestionResult.class);
		Criterion plNum = Restrictions.eq("playerNumber", playerNumber);
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
	
}
