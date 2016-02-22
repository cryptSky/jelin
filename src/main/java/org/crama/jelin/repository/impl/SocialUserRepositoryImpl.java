package org.crama.jelin.repository.impl;

import javax.transaction.Transactional;

import org.crama.jelin.model.SocialUser;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.SocialUserRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("socialUserRepository")
public class SocialUserRepositoryImpl implements SocialUserRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public SocialUser findByProviderIdAndProviderUserId(String providerId, String providerUserId) {
		//System.out.println(providerId + ", " + providerUserId);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SocialUser.class);
		Criterion prId = Restrictions.eq("providerId", providerId);
		Criterion prUsrId = Restrictions.eq("providerUserId", providerUserId);
		criteria.add(Restrictions.and(prId, prUsrId));
		SocialUser socialUser = (SocialUser)criteria.uniqueResult(); 
		return socialUser;
	}

	@Override
	public SocialUser getUserByPhoneAndProviderId(String phone, String providerId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SocialUser.class);
		Criterion prId = Restrictions.eq("providerId", providerId);
		Criterion phoneCr = Restrictions.eq("phone", phone);
		criteria.add(Restrictions.and(prId, phoneCr));
		SocialUser socialUser = (SocialUser)criteria.uniqueResult(); 
		return socialUser;
	}

	@Override
	public SocialUser getUserByEmailAndProviderId(String email, String providerId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SocialUser.class);
		Criterion prId = Restrictions.eq("providerId", providerId);
		Criterion emailCr = Restrictions.eq("email", email);
		criteria.add(Restrictions.and(prId, emailCr));
		SocialUser socialUser = (SocialUser)criteria.uniqueResult(); 
		return socialUser;
	}

	@Transactional
	@Override
	public void saveSocialUser(SocialUser socialUser) {
		Session session = sessionFactory.getCurrentSession();	
		session.save(socialUser);
	}

	@Transactional
	@Override
	public void update(SocialUser existSocialUser) {
		Session session = sessionFactory.getCurrentSession();	
		session.update(existSocialUser);
	}

	@Override
	public SocialUser getSocialUser(User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SocialUser.class);
		criteria.add(Restrictions.eq("user", user));
		
		SocialUser socialUser = (SocialUser)criteria.uniqueResult(); 
		return socialUser;
	}

}
