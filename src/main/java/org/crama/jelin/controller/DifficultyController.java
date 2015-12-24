package org.crama.jelin.controller;

import java.util.List;

import org.crama.jelin.model.Difficulty;
import org.crama.jelin.service.DifficultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DifficultyController {
	
	@Autowired
	private DifficultyService difficultyService;

	@RequestMapping(value="/api/question/difficulty/", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Difficulty> getAllDifficulties() {
		List<Difficulty> difficultyList = difficultyService.getAllDifficulties();
        return difficultyList;
	}
	
}