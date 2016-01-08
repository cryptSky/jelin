package org.crama.jelin.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.crama.jelin.model.GameRound;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	public void updateRound(GameRound round) {
		Session session = sessionFactory.getCurrentSession();	
		session.update(round);
		
	}
	

}
