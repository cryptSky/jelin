package org.crama.jelin.repository;

import javax.transaction.Transactional;

import org.crama.jelin.model.GameState;
import org.crama.jelin.model.ProcessStatus;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;
import org.crama.jelin.model.UserRole;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final String GET_USER_BY_USERNAME = "FROM User "
			+ "WHERE username = :username";
	
	private static final String GET_USER_MODEL = "FROM UserModel "
										+ "WHERE username = :username";
	
	private static final String GET_USER_MODEL_BY_EMAIL = "FROM UserModel "
			+ "WHERE email = :email";
	
	private static final String GET_USER_ROLE = "FROM UserRole "
										+ "WHERE role = :role";
	
	/*private static final String SAVE_USER_ROLE = "INSERT INTO user_role(USERNAME, ROLE_ID) "
										+ "VALUES (?,?)";*/
	
	public static final String GET_PROCESS_STATUS = "FROM ProcessStatus " +
			"WHERE status = :status ";
	
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
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		//if (!tx.wasCommitted()) {
			tx.commit();
		//}

	}

	@Override
	public void saveUser(User user) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Transaction tx = session.beginTransaction();
		try {
			
			session.save(user);
			
		}
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		//if (!tx.wasCommitted()) {
			tx.commit();
		//}
		
	}

	
	

	/*@Override
	public void saveUserRoles(UserModel model) {
		Session session = sessionFactory.getCurrentSession();
		Set<UserRole> roles = model.getRoles();
		for (UserRole role: roles) {
			Transaction tx = session.beginTransaction();
			try {
				
				Query query = session.createSQLQuery(SAVE_USER_ROLE);
				query.setParameter(0, model.getUsername());
				query.setParameter(1, role.getRoleId());
				
				int rows = query.executeUpdate();
				tx.commit();
			}
			catch (HibernateException e) {
				e.printStackTrace();
				tx.rollback();
			}
		}
		
	}*/

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
		
	}

	@Override
	public User getUser(int userId) {
		
		return (User)sessionFactory.getCurrentSession().get(User.class, userId);
	}

	@Override
	public ProcessStatus getProcessStatus(String status) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_PROCESS_STATUS);
		query.setParameter("status", status);
		ProcessStatus processStatus = (ProcessStatus)query.uniqueResult();
		return processStatus;
	}

}
