package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Difficulty;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("questionRepository")
public class QuestionRepositoryImpl implements QuestionRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final String GET_ALL_DIFFICULTIES = "FROM Difficulty";
	
	@Override
	public List<Difficulty> getAllDifficulties() {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_DIFFICULTIES);
		
		@SuppressWarnings("unchecked")
		List<Difficulty> difficulties = query.list();
	    	
		return difficulties;
	}

}
