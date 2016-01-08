package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Difficulty;

public interface DifficultyService {

	List<Difficulty> getAllDifficulties();
	
	Difficulty getDifficultyById(int diffId);

	void checkDifficultyNotNull(Difficulty diff) throws GameException;

}
