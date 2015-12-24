package org.crama.jelin.controller;


import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.service.CategoryService;
import org.crama.jelin.service.DifficultyService;
import org.crama.jelin.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

	@Autowired
	private GameService gameService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private DifficultyService difficultyService;
	
	
	@RequestMapping(value="/api/game", method=RequestMethod.PUT, params={"theme", "random"})
	@ResponseStatus(HttpStatus.OK)
    public boolean initGame(@RequestParam int theme, @RequestParam boolean random) {
		Category category = categoryService.getThemeById(theme);
		if (category == null)
		{
			return false;
		}
		return gameService.initGame(category, random);
	}		

	@RequestMapping(value="/api/game", method=RequestMethod.POST, params={"difficulty", "game"})
	@ResponseStatus(HttpStatus.OK)
    public boolean updateDifficulty(@RequestParam int difficulty, @RequestParam int game) {
		Difficulty diff = difficultyService.getDifficultyById(difficulty);
		if (diff == null)
		{
			return false;
		}
		return gameService.updateDifficulty(game, diff);
	}
}
