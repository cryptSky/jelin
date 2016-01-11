package org.crama.jelin.repository;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameOpponent;
import org.crama.jelin.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("gameRepository")
public class GameRepositoryImpl implements GameRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void updateGame(Game game) {
		
		Session session = sessionFactory.getCurrentSession();	
		session.update(game);

	}

}
