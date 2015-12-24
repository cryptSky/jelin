package org.crama.jelin.controller;


import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.ProcessStatus;
import org.crama.jelin.model.User;
import org.crama.jelin.service.CategoryService;
import org.crama.jelin.service.DifficultyService;
import org.crama.jelin.service.GameService;
import org.crama.jelin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/api/game", method=RequestMethod.PUT, params={"theme", "random"})
	@ResponseStatus(HttpStatus.CREATED)
    public boolean initGame(@RequestParam int theme, @RequestParam boolean random) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		        UserDetails userDetails = (UserDetails)auth.getPrincipal();
		        User creator = userService.getUserByUsername(userDetails.getUsername());
		        
		        
		        Category category = categoryService.getThemeById(theme);
		        if (creator == null || category == null) {
		        	return false;
		        }
		        else if (!creator.getProcessStatus().getStatus().equals(ProcessStatus.FREE)) {
		        	System.out.println("User is in status: " + creator.getProcessStatus().getStatus());
		        	return false;
		        	
		        }
		        else {
		        	if (gameService.initGame(creator, category, random)) {
		        		userService.updateUserProcessStatus(creator, ProcessStatus.CALLING);
		        		return true;
		        	}
		        	else {
		        		return false;
		        	}
		        }
		}
		else {
			return false;
		}
		
	}		

	@RequestMapping(value="/api/game", method=RequestMethod.POST, params={"difficulty"})
	@ResponseStatus(HttpStatus.OK)
    public boolean updateDifficulty(@RequestParam int difficulty) {
		Difficulty diff = difficultyService.getDifficultyById(difficulty);
		if (diff == null) {
			return false;
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		        UserDetails userDetails = (UserDetails)auth.getPrincipal();
		        User creator = userService.getUserByUsername(userDetails.getUsername());
		        
		        if (creator == null) {
		        	return false;
		        }
		        else if (!creator.getProcessStatus().getStatus().equals(ProcessStatus.CALLING)) {
		        	System.out.println("User is in status: " + creator.getProcessStatus().getStatus() + ". User should be in status calling.");
		        	return false;
		        	
		        }
		        else {
		        	Game game = gameService.getCreatedGame(creator);
		        	return gameService.updateDifficulty(game, diff);
		        }
		}
		else {
			return false;
		}
		
	}
}
