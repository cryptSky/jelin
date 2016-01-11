package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Question;
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
	public List<Question> getQuestionsByCategory(Category category) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Question.class);
		criteria.add(Restrictions.eq("category", category));
				
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getQuestionsByCategoryAndDifficulty(Category category, Difficulty difficulty) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Question.class);
		Criterion cat = Restrictions.eq("category", category);
		Criterion diff = Restrictions.eq("difficulty", difficulty);
		criteria.add(Restrictions.and(cat, diff));
			
		return criteria.list();
	}

}
