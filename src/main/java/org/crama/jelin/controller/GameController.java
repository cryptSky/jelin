package org.crama.jelin.controller;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.User;
import org.crama.jelin.model.Constants.ProcessStatus;
import org.crama.jelin.model.Game;
import org.crama.jelin.service.CategoryService;
import org.crama.jelin.service.DifficultyService;
import org.crama.jelin.service.GameInitService;
import org.crama.jelin.service.GameService;
import org.crama.jelin.service.OpponentSearchService;
import org.crama.jelin.service.UserDetailsServiceImpl;
import org.crama.jelin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private GameService gameService;
	@Autowired
	private GameInitService gameInitService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private DifficultyService difficultyService;
	@Autowired
	private UserService userService;
		
	@RequestMapping(value="/api/game/start", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
    public boolean startGame() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		        UserDetails userDetails = (UserDetails)auth.getPrincipal();
		        User creator = userService.getUserByUsername(userDetails.getUsername());
		        
		        Game game = gameInitService.getCreatedGame(creator);
		        if (game == null)
		        {
		        	logger.error("Game not found! User " + creator + " has not created any game"); 
		        	return false;
		        }
		        
		        return true;
		}       
		
		return false;
	}		
}
