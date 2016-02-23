package org.crama.jelin.controller;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.service.DifficultyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DifficultyController {
	
	private static final Logger logger = LoggerFactory.getLogger(CharacterController.class);
	
	@Autowired
	private DifficultyService difficultyService;

	@RequestMapping(value="/api/question/difficulty", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Difficulty> getAllDifficulties() {
		List<Difficulty> difficultyList = difficultyService.getAllDifficulties();
        return difficultyList;
	}
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		logger.error(ge.getMessage());
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());	
        return re;
    }
	
}
