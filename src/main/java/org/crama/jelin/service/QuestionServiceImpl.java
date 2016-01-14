package org.crama.jelin.service;

import java.util.List;
import java.util.Random;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Question;
import org.crama.jelin.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Override
	public Question getRandomQuestion(Category category, Difficulty diff) throws GameException {
		List<Question> questions = questionRepository
									.getQuestionsByCategoryAndDifficulty(category, diff);
		
		if (questions.size() == 0)
		{
			throw new GameException(0, "There is no questions with category: "
										+ category.toString() +
										" and difficulty" + diff.toString());
		}
		
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(questions.size());
		Question question = questions.get(index);
		
		return question;
	}

}
