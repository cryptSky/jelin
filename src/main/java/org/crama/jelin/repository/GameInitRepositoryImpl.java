package org.crama.jelin.repository;

import javax.transaction.Transactional;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameOpponent;
import org.crama.jelin.model.Constants.GameState;
import org.crama.jelin.model.Constants.InviteStatus;
import org.crama.jelin.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("gameInitRepository")
public class GameInitRepositoryImpl implements GameInitRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	public static final String GET_CREATED_GAME_SQL = "SELECT * FROM game " +
			"WHERE STATE_ID = ( " +
				"SELECT STATE_ID FROM gamestate " +
				"WHERE STATE = :state) " +
			"AND ID = ( " +
				"SELECT GAME_ID FROM gameuser " +
				"WHERE USER_ID = :userId);";
			
	public static final String GET_CREATED_GAME = "FROM Game " +
			"WHERE creator = :creator " +
			"AND gameState = :gameState";
	
	public static final String GET_GAME_STATE = "FROM GameState " +
			"WHERE state = :state ";
	
	public static final String GET_INVITE_STATUS = "FROM InviteStatus " +
			"WHERE status = :status ";
	
	//user_id
	//inviteStatus = open
	//ex. select p from Parent p join p.childList c
    //where p.name = 'John' and c.favoriteColor = 'blue'
	public static final String GET_INVITE_GAME = "SELECT g FROM Game g JOIN g.gameOpponents o " +
			"WHERE g.gameState = :gameState " +
			"AND o.user = :user " +
			"AND o.inviteStatus = :inviteStatus";
	
	public static final String GET_GAME_OPPONENT = "FROM GameOpponent " +
			"WHERE game = :game "
			+ "AND user = :user";
	
	public static final String DELETE_GAME_OPPONENT = "DELETE GameOpponent " +
			"WHERE game = :game "
			+ "AND user = :user";
	
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
		System.out.println(game);
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
		
		Query query = sessionFactory.getCurrentSession().createQuery(GET_CREATED_GAME);
		
		query.setParameter("creator", creator);
		query.setParameter("gameState", GameState.CREATED);
		Game game = (Game)query.uniqueResult();
		System.out.println(game);
		return game;
		
		/*Query query = sessionFactory.getCurrentSession().getNamedQuery("getCreatedGameSQL");
		query.setParameter("state", GameState.CREATED);
		query.setParameter("userId", creator.getId());
		Game game = (Game)query.uniqueResult();
		System.out.println(game);
		return game;*/
		
	}

	@Override
	public Game getInviteGame(User user) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_INVITE_GAME);
		query.setParameter("gameState", GameState.CREATED);
		query.setParameter("user", user);
		query.setParameter("inviteStatus", InviteStatus.OPEN);
		Game inviteGame = (Game)query.uniqueResult();
		System.out.println(inviteGame);
		return inviteGame;
		
		
	}

	@Override
	public GameOpponent getGameOpponent(Game game, User opponent) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_GAME_OPPONENT);
		query.setParameter("game", game);
		query.setParameter("user", opponent);
		GameOpponent go = (GameOpponent)query.uniqueResult();
		return go;
	}
	
	@Override
	public void clearSession() {
		sessionFactory.getCurrentSession().clear();
	}

	@Transactional
	@Override
	public boolean removeGameOpponent(Game game, User user) {
		Query query = sessionFactory.getCurrentSession().createQuery(DELETE_GAME_OPPONENT);
		query.setParameter("game", game);
		query.setParameter("user", user);
		int rowsUpdated = query.executeUpdate();
		return rowsUpdated == 0? false: true; 
	}

}
