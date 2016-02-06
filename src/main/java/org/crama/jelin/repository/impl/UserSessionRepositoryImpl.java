package org.crama.jelin.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.crama.jelin.model.UserSession;

import java.util.List;

import org.crama.jelin.model.User;

@Repository("sessionRepository")
public class UserSessionRepositoryImpl implements UserSessionRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public UserSession getSession(User user) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserSession.class);
		criteria.add(Restrictions.eq("user", user));
		
		return (UserSession) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public void saveOrUpdate(UserSession userSession) {
		Session session = sessionFactory.getCurrentSession();	
		session.saveOrUpdate(userSession);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserSession> getAllSessions(User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserSession.class);
		criteria.add(Restrictions.eq("user", user));
		
		return criteria.list();
	}

}
