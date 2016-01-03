package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Question;
import org.crama.jelin.model.User;
import org.crama.jelin.model.Constants.NetStatus;
import org.crama.jelin.model.Constants.ProcessStatus;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("questionRepository")
public class QuestionRepositoryImpl implements QuestionRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getQuestionsByCategory(int categoryID) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Question.class);
		criteria.add(Restrictions.eq("category.id", categoryID));
				
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getQuestionsByCategoryAndDifficulty(int categoryID, int difficultyID) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Question.class);
		Criterion category = Restrictions.eq("category.id", categoryID);
		Criterion difficulty = Restrictions.eq("difficulty.id", difficultyID);
		criteria.add(Restrictions.and(category, difficulty));
			
		return criteria.list();
	}

}
