package org.crama.jelin.repository.impl;

import java.util.Date;
import java.util.List;

import org.crama.jelin.model.Bonus;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserBonus;
import org.crama.jelin.repository.BonusRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("bonusRepository")
public class BonusRepositoryImpl implements BonusRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public UserBonus getUserPromoBonus(String code, User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserBonus.class, "userBonus")
			    .createAlias("userBonus.bonus", "bonus")
			    .add(Restrictions.eq("userBonus.user", user))
			    .add(Restrictions.eq("bonus.condPromocode", code));
		UserBonus userBonus = (UserBonus)criteria.uniqueResult();
		
		return userBonus;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserBonus> getUserBonus(Bonus bonus, User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserBonus.class);
		Criterion bonusCr = Restrictions.eq("bonus", bonus);
		Criterion userCr = Restrictions.eq("user", user);
		
		criteria.add(Restrictions.and(bonusCr, userCr));
		
		List<UserBonus> userBonusList = (List<UserBonus>)criteria.list();
		return userBonusList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBonus> getDailyUserBonus(Bonus bonus, User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserBonus.class);
		Criterion bonusCr = Restrictions.eq("bonus", bonus);
		Criterion userCr = Restrictions.eq("user", user);
		Criterion dateCr = Restrictions.eq("receiveDate", new Date());
		criteria.add(Restrictions.and(bonusCr, userCr, dateCr));
		
		List<UserBonus> userBonusList = (List<UserBonus>)criteria.list();
		return userBonusList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Bonus> getBonusesByPromocode(String code) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bonus.class);
		criteria.add(Restrictions.eq("condPromocode", code));
		
		return criteria.list();
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public List<Bonus> getBonusesForEarlyRegistration() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bonus.class);
		criteria.add(Restrictions.eq("forEarlyRegister", true));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bonus> getBonusesForDaysInGame(int daysInGame) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bonus.class);
		criteria.add(Restrictions.eq("condDays", daysInGame));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bonus> getBonusesForSMMInvites(int smmInvites) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bonus.class);
		criteria.add(Restrictions.eq("condInvites", smmInvites));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Bonus> getBonusesForSMMShares(int smmShares) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bonus.class);
		criteria.add(Restrictions.eq("condShares", smmShares));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bonus> getBonusesForInitiatedGames(int gamesInitiated) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bonus.class);
		criteria.add(Restrictions.eq("condInitiated", gamesInitiated));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bonus> getBonusesForPlayedGames(int gamesPlayed) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bonus.class);
		criteria.add(Restrictions.eq("condPlayed", gamesPlayed));
		
		return criteria.list();
	}

	

}
