package org.crama.jelin.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.Constants.InviteStatus;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("gameRoundRepository")
public class GameRoundRepositoryImpl implements GameRoundRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void saveOrUpdateRounds(List<GameRound> gameRounds) {
		for (GameRound round: gameRounds)
		{
			Session session = sessionFactory.getCurrentSession();	
			session.saveOrUpdate(round);
		}		

	}

	@Override
	@Transactional
	public void updateRound(GameRound round) {
		Session session = sessionFactory.getCurrentSession();	
		session.update(round);
		
	}

	
	@Override
	public GameRound getRoundByNumber(int roundNumber, Game game)
	{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GameRound.class);
		Criterion roundNum = Restrictions.eq("roundNumber", roundNumber);
		Criterion gameC = Restrictions.eq("game", game);
		
		criteria.add(Restrictions.and(roundNum, gameC));
		
		return (GameRound) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GameRound> getAllRoundsByGame(Game game) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GameRound.class);
		criteria.add(Restrictions.eq("game", game));
		
		return criteria.list();
	}

}
