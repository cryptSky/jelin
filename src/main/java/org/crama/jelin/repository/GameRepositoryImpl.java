package org.crama.jelin.repository;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("gameRepository")
public class GameRepositoryImpl implements GameRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean initGame(Category theme, boolean random) {
		Game game = new Game(theme, random);
		
		try
		{	
			sessionFactory.getCurrentSession().beginTransaction();
			sessionFactory.getCurrentSession().save(game);
			sessionFactory.getCurrentSession().getTransaction().commit();			
		}
		catch (HibernateException e) {
			e.printStackTrace();
			sessionFactory.getCurrentSession().getTransaction().rollback();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean updateDifficulty(int gameId, Difficulty difficulty) {
				
		try
		{	
			sessionFactory.getCurrentSession().beginTransaction();
			Game game = (Game)sessionFactory.getCurrentSession().get(Game.class, gameId);
			game.setDifficulty(difficulty);
			sessionFactory.getCurrentSession().save(game);
			sessionFactory.getCurrentSession().getTransaction().commit();			
		}
		catch (HibernateException e) {
			e.printStackTrace();
			sessionFactory.getCurrentSession().getTransaction().rollback();
			return false;
		}
		
		return true;
	}

}
