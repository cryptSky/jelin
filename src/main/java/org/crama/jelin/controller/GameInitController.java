package org.crama.jelin.controller;


import java.util.Set;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.ProcessStatus;
import org.crama.jelin.model.User;
import org.crama.jelin.service.CategoryService;
import org.crama.jelin.service.DifficultyService;
import org.crama.jelin.service.GameInitService;
import org.crama.jelin.service.UserDetailsServiceImpl;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameInitController {

	@Autowired
	private GameInitService gameInitService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private DifficultyService difficultyService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
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
		        	if (gameInitService.initGame(creator, category, random)) {
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
		        	Game game = gameInitService.getCreatedGame(creator);
		        	return gameInitService.updateDifficulty(game, diff);
		        }
		}
		else {
			return false;
		}
		
	}
	
	@RequestMapping(value = "/api/game/opponents", method = RequestMethod.GET)
	public Set<User> getOpponents() {
		User creator = userDetailsService.getPrincipal();
		//1. check if user have created game
		System.out.println(creator);
		Game game = gameInitService.getCreatedGame(creator);
		System.out.println(game);
		if (game == null) {
			return null;
		}
		//2. check if user set difficulty to the game
		if (game.getDifficulty() == null) {
			return null;
		}
		//3. check if game is not random
		if (game.getRandom()) {
			return null;
		}
		//4. check if user state is calling
		if (creator.getProcessStatus().getStatus() == ProcessStatus.CALLING) {
			return null;
		}
		Set<User> opponents = game.getGameOpponents();
		return opponents;
	}
	
	@RequestMapping(value = "/api/game/opponents/kick", method = RequestMethod.POST)
	public void removetOpponent(@RequestParam int user) {
		User creator = userDetailsService.getPrincipal();
		//1. check if user have created game
		System.out.println(creator);
		Game game = gameInitService.getCreatedGame(creator);
		System.out.println(game);
		if (game == null) {
			return;
		}
		//2. check if user set difficulty to the game
		if (game.getDifficulty() == null) {
			return;
		}
		//3. check if game is not random
		if (game.getRandom()) {
			return;
		}
		//4. check if user state is calling
		if (creator.getProcessStatus().getStatus() == ProcessStatus.CALLING) {
			return;
		}
		//5. check if user is in opponent set
		gameInitService.removeOpponent(game, user);
		
	}
	@RequestMapping(value = "/api/game/userGameState", method = RequestMethod.GET)
	public @ResponseBody String getUserGameStatus() {
		User creator = userDetailsService.getPrincipal();
		return creator.getProcessStatus().getStatus();
	}
}
