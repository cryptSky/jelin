package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.repository.DifficultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("difficultyService")
public class DifficultyServiceImpl implements DifficultyService {

	@Autowired
	private DifficultyRepository difficultyRepository;
	
	@Override
	public List<Difficulty> getAllDifficulties() {
		
		return difficultyRepository.getAllDifficulties();
	}

	@Override
	public Difficulty getDifficultyById(int diffId) {
		return difficultyRepository.getDifficultyById(diffId);
	}

	@Override
	public void checkDifficultyNotNull(Difficulty diff) throws GameException {
		if (diff == null) {
			throw new GameException(301, "Difficulty is null");
		}
	}

}
