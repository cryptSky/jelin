package org.crama.jelin.repository.impl;

import javax.transaction.Transactional;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserInfo;
import org.crama.jelin.repository.UserInfoRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userInfoRepository")
public class UserInfoRepositoryImpl implements UserInfoRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final String GET_USER_INFO = "FROM UserInfo "
			+ "WHERE user = :user";
	
	@Override
	public UserInfo getUserInfo(User user) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USER_INFO);
		query.setParameter("user", user);
		UserInfo userInfo = (UserInfo)query.uniqueResult();
		return userInfo;
	}

	@Override
	@Transactional
	public void updateUserInfo(UserInfo userInfo) {
		Session session = sessionFactory.getCurrentSession();
		
		session.update(userInfo);
	}
	
	@Override
	@Transactional
	public void updateUserFullInfo(User user, UserInfo userInfo) {
		Session session = sessionFactory.getCurrentSession();
		
		UserInfo oldInfo = getUserInfo(user);
		UserInfo dbInfo = (UserInfo)session.get(UserInfo.class, oldInfo.getId());
		
		dbInfo.update(userInfo);
		
		session.update(dbInfo);
	}

}
