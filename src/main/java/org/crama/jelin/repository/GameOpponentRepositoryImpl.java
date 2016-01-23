package org.crama.jelin.repository;


import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameOpponent;
import org.crama.jelin.model.User;
import org.crama.jelin.model.Constants.GameState;
import org.crama.jelin.model.Constants.InviteStatus;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("gameOpponentRepository")
public class GameOpponentRepositoryImpl implements GameOpponentRepository {

	public static final String GET_PLAYER_GAME = "SELECT g FROM Game g JOIN g.gameOpponents o " +
			"WHERE g.gameState <> :gameState " +
			"AND o.user = :user " +
			"AND o.inviteStatus = :inviteStatus";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Game getGameByPlayer(User player) {
		
		Query query = sessionFactory.getCurrentSession().createQuery(GET_PLAYER_GAME);
		query.setParameter("gameState", GameState.ENDED);
		query.setParameter("user", player);
		query.setParameter("inviteStatus", InviteStatus.ACCEPTED);
		Game playerGame = (Game)query.uniqueResult();
		
		return playerGame;
		
	}

	
}
