package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.Difficulty;
import org.crama.jelin.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Override
	public List<Difficulty> getAllDifficulties() {
		
		return questionRepository.getAllDifficulties();
	}

}
