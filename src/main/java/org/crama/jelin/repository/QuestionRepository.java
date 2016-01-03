package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Question;

public interface QuestionRepository {
	List<Question> getQuestionsByCategory(int categoryID);
	List<Question> getQuestionsByCategoryAndDifficulty(int categoryID, int difficultyID);
}
