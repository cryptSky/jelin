package org.crama.jelin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Question;
import org.crama.jelin.repository.QuestionRepository;
import org.crama.jelin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Override
	public Question getRandomQuestion(Category category, Difficulty diff) throws GameException {
		List<Question> questions = questionRepository.getQuestionsByCategoryAndDifficulty(category, diff);
		
		if (questions.size() == 0)
		{
			throw new GameException(515, "There is no questions with category: "
										+ category.getName() +
										" and difficulty " + diff.getName());
		}
		
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(questions.size());
		Question question = questions.get(index);
		
		return question;
	}

	@Override
	public List<Question> getRandomQuestionList(Category category, Difficulty difficulty, int questionsNumber)
			throws GameException {
		List<Question> resultQuestionList = new ArrayList<Question>();
		List<Question> questions = questionRepository.getQuestionsByCategoryAndDifficulty(category, difficulty);
		
		if (questions.size() == 0) {
			throw new GameException(515, "There is no questions with category: "
										+ category.getName() +
										" and difficulty " + difficulty.getName());
		}
		
		if (questions.size() < questionsNumber) {
			throw new GameException(515, "There is not enough questions with given category and difficulty");
		}
		
		Collections.shuffle(questions);
        for (int i = 0; i < questionsNumber; i++) {
            Question q = questions.get(i);
            resultQuestionList.add(q);
        }
		
		return resultQuestionList;
	}

}
