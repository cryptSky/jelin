package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Question;

public interface QuestionRepository {
	List<Question> getQuestionsByCategory(Category category);
	List<Question> getQuestionsByCategoryAndDifficulty(Category category, Difficulty difficulty);
}
