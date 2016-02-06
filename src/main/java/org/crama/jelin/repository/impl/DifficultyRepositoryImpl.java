package org.crama.jelin.repository.impl;

import java.util.List;

import org.crama.jelin.model.Difficulty;
import org.crama.jelin.repository.DifficultyRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("difficultyRepository")
public class DifficultyRepositoryImpl implements DifficultyRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final String GET_ALL_DIFFICULTIES = "FROM Difficulty";
	
	private static final String GET_DIFFICULTY_BY_ID = "FROM Difficulty "
			+ "WHERE difficulty_id = :diffId";
	
	@Override
	public List<Difficulty> getAllDifficulties() {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_DIFFICULTIES);
		
		@SuppressWarnings("unchecked")
		List<Difficulty> difficulties = query.list();
	    	
		return difficulties;
	}

	@Override
	public Difficulty getDifficultyById(int diffId) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_DIFFICULTY_BY_ID);
		query.setParameter("diffId", diffId);
		Difficulty difficulty = (Difficulty)query.uniqueResult();
		return difficulty;
	}

}
