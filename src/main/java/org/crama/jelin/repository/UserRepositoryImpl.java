package org.crama.jelin.repository;

import org.crama.jelin.model.UserModel;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final String GET_USER_MODEL = "FROM UserModel "
										+ "WHERE username = :username";
	
	@Override
	public UserModel getUserModel(String username) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_USER_MODEL);
		query.setParameter("username", username);
		UserModel user = (UserModel)query.uniqueResult();
		return user;

	}

}
