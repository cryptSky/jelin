package org.crama.jelin.repository;


import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameOpponent;
import org.crama.jelin.model.User;
import org.crama.jelin.model.Constants.InviteStatus;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("gameOpponentRepository")
public class GameOpponentRepositoryImpl implements GameOpponentRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Game getGameByPlayer(User player) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GameOpponent.class);
		Criterion user = Restrictions.eq("user", player);
		Criterion isAccepted = Restrictions.eq("inviteStatus", InviteStatus.ACCEPTED);
		criteria.add(Restrictions.and(user, isAccepted));
		
		GameOpponent opponent = (GameOpponent) criteria.uniqueResult();
		
		return opponent.getGame();
	}
	
	

}
