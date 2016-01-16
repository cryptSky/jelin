package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Question;

public interface QuestionService {
	
	Question getRandomQuestion(Category category, Difficulty diff) throws GameException;

	List<Question> getRandomQuestionList(Category category, Difficulty difficulty, int questionsNumber) throws GameException;
}
