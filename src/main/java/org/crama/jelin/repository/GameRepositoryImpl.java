package org.crama.jelin.repository;

import javax.transaction.Transactional;

import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameState;
import org.crama.jelin.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("gameRepository")
public class GameRepositoryImpl implements GameRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	public static final String GET_CREATED_GAME_HQL = "FROM GameUser " +
			"WHERE user = :user AND ";
	
	public static final String GET_CREATED_GAME_SQL = "SELECT * FROM game " +
			"WHERE STATE_ID = ( " +
				"SELECT STATE_ID FROM gamestate " +
				"WHERE STATE = :state) " +
			"AND ID = ( " +
				"SELECT GAME_ID FROM gameuser " +
				"WHERE USER_ID = :userId);";
			
	
	@Override
	@Transactional
	public boolean saveGame(Game game) {
		
		Session session = sessionFactory.getCurrentSession();
		session.save(game);
		
		return true;
		
		/*Game game = new Game(theme, random);
		Session session = sessionFactory.getCurrentSession();
		
		try
		{	
			session.beginTransaction();
			session.save(game);
			session.getTransaction().commit();			
		}
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
		
		return true;*/
	}

	@Override
	@Transactional
	public boolean updateGame(Game game) {
		Session session = sessionFactory.getCurrentSession();		
		
		if (game == null) return false;
		
		session.update(game);
		return true;
	}
	/*@Override
	@Transactional
	public boolean updateDifficulty(int gameId, Difficulty difficulty) {
		Session session = sessionFactory.getCurrentSession();		
		
		Game game = (Game)session.get(Game.class, gameId);
		if (game == null) return false;
		System.out.println(difficulty);
		game.setDifficulty(difficulty);
		session.update(game);
		return true;
		
		try
		{	
			session.beginTransaction();
			Game game = (Game)session.get(Game.class, gameId);
			if (game == null) return false;
			System.out.println(difficulty);
			game.setDifficulty(difficulty);
			session.update(game);
			session.getTransaction().commit();			
		}
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
		
		return true;
	}*/

	@Override
	public Game getCreatedGame(User creator) {
		//Query query = sessionFactory.getCurrentSession().createSQLQuery(GET_CREATED_GAME_SQL);
		
		Query query = sessionFactory.getCurrentSession().getNamedQuery("getCreatedGameSQL");
		query.setParameter("state", GameState.CREATED);
		query.setParameter("userId", creator.getId());
		Game game = (Game)query.uniqueResult();
		System.out.println(game);
		return game;
	}

}
