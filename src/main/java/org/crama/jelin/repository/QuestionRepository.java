package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Difficulty;

public interface QuestionRepository {

	List<Difficulty> getAllDifficulties();

}
