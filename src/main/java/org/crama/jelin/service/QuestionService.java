package org.crama.jelin.service;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Question;

public interface QuestionService {
	Question getRandomQuestion(Category category, Difficulty diff);
}
