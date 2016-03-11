package org.crama.jelin.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.crama.jelin.model.Constants.NetStatus;
import org.crama.jelin.model.Constants.ProcessStatus;
import org.crama.jelin.model.Constants.Readiness;
import org.crama.jelin.model.Constants.UserType;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;
import org.crama.jelin.model.UserRole;
import org.crama.jelin.repository.UserRepository;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

	private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final String GET_USER_BY_USERNAME = "FROM User "
			+ "WHERE username = :username";
	
	private static final String GET_USER_MODEL = "FROM UserModel "
										+ "WHERE username = :username";
	
	private static final String GET_USER_MODEL_BY_EMAIL = "FROM UserModel "
			+ "WHERE email = :email";
	
	private static final String GET_USER_BY_EMAIL = "FROM User "
			+ "WHERE email = :email";
	
	private static final String GET_USER_ROLE = "FROM UserRole "
										+ "WHERE role = :role";
	
	private static final String GET_ALL_USERS = "FROM User ";
	
	private static final String UPDATE_ALL_USERS_NET_STATUS = "UPDATE User SET netStatus = :status "
										+ "WHERE id <> :id";

	private static final String UPDATE_USERS_NET_STATUS = "UPDATE User SET netStatus = :status "
			+ "WHERE id = :id";

		
	@Override
	public UserModel getUserModel(String username) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USER_MODEL);
		query.setParameter("username", username);
		UserModel user = (UserModel)query.uniqueResult();
		return user;

	}
	
	@Override
	public UserModel getUserModelEmail(String email) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USER_MODEL_BY_EMAIL);
		query.setParameter("email", email);
		UserModel user = (UserModel)query.uniqueResult();
		return user;
	}

	@Override
	public void saveUserModel(UserModel model) {
		Session session = sessionFactory.getCurrentSession();
		
		Transaction tx = session.beginTransaction();
		try {
			
			session.save(model);
			
		}
		catch (HibernateException e) {
			logger.error(e.getMessage());
			session.getTransaction().rollback();
		}
		
		tx.commit();

	}

	@Override
	public void saveUser(User user) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Transaction tx = session.beginTransaction();
		try {
			
			session.save(user);
			
		}
		catch (HibernateException e) {
			logger.error(e.getMessage());
			session.getTransaction().rollback();
		}
		
		tx.commit();
		
	}


	@Override
	public UserRole getUserRole(String roleUser) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USER_ROLE);
		query.setParameter("role", roleUser);
		UserRole role = (UserRole)query.uniqueResult();
		return role;
	}

	@Override
	public User getUserByUsername(String username) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USER_BY_USERNAME);
		query.setParameter("username", username);
		User user = (User)query.uniqueResult();
		return user;
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		Session session = sessionFactory.getCurrentSession();	
		session.update(user);
		session.flush();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersShadowAndFree() {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		Criterion isShadow = Restrictions.eq("netStatus", NetStatus.SHADOW);
		Criterion isFree = Restrictions.eq("processStatus", ProcessStatus.FREE);
		criteria.add(Restrictions.and(isShadow, isFree));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersOnlineAndCalling(int exceptUserID) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		Criterion isOnline = Restrictions.eq("netStatus", NetStatus.ONLINE);
		Criterion isCalling = Restrictions.eq("processStatus", ProcessStatus.CALLING);
		criteria.add(Restrictions.and(isOnline, isCalling))
				.add(Restrictions.ne("id", exceptUserID));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersOnlineAndFree() {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		Criterion isOnline = Restrictions.eq("netStatus", NetStatus.ONLINE);
		Criterion isFree = Restrictions.eq("processStatus", ProcessStatus.FREE);
		criteria.add(Restrictions.and(isOnline, isFree));
		
		return criteria.list();
	}

	
	@Override
	public User getUser(int userId) {
		
		return (User)sessionFactory.getCurrentSession().get(User.class, userId);
	}

	@Override
	public List<User> getAllUsers() {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_USERS);
		
		@SuppressWarnings("unchecked")
		List<User> user = (List<User>)query.list();
		return user;
	}

	@Override
	public int getMaxUserId() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		
		criteria.setProjection(Projections.max("id"));
		int maxId = (int)criteria.uniqueResult();
		
		return maxId;
	}

	@Override
	@Transactional
	public void updateAllUsersNetStatus(User user, NetStatus status) {
		
		Query query = sessionFactory.getCurrentSession().createQuery(UPDATE_ALL_USERS_NET_STATUS);
		query.setParameter("status", status);
		query.setParameter("id", user.getId());
		query.executeUpdate();
		
	}

	@Override
	@Transactional
	public void updateNetStatus(User user, NetStatus s) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(UPDATE_USERS_NET_STATUS);
		query.setParameter("status", s);
		query.setParameter("id", user.getId());
		query.executeUpdate();
		session.flush();
	}

	@Override
	public User getByEmailAddress(String email) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USER_BY_EMAIL);
		query.setParameter("email", email);
		User user = (User)query.uniqueResult();
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> getPlayersNotWithReadiness(Readiness readiness, Game game) {
		List<Integer> ids = new ArrayList<Integer>();
		for (User player: game.getHumans())
		{
			ids.add(player.getId());
		}
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		criteria.add(Restrictions.in("id", ids))
				.add(Restrictions.ne("readiness", readiness))
				.add(Restrictions.eq("type", UserType.HUMAN))
				.add(Restrictions.eq("netStatus", NetStatus.ONLINE));
		return criteria.list();
	}

	@Override
	public void lock(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.buildLockRequest(LockOptions.NONE).lock(user);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllBots() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		Criterion isBot = Restrictions.eq("type", UserType.BOT);
		criteria.add(isBot);
		return criteria.list();
	}

	
}
