package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Difficulty;

public interface DifficultyRepository {

	List<Difficulty> getAllDifficulties();
	Difficulty getDifficultyById(int diffId);

}
