package org.crama.jelin.controller;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.GameOpponentRepository;
import org.crama.jelin.service.CategoryService;
import org.crama.jelin.service.DifficultyService;
import org.crama.jelin.service.GameInitService;
import org.crama.jelin.service.GameService;
import org.crama.jelin.service.UserDetailsServiceImpl;
import org.crama.jelin.service.UserService;
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
public class GameController {

	private static final Logger logger = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private GameService gameService;
	@Autowired
	private GameInitService gameInitService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
		
	@RequestMapping(value="/api/game/start", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public boolean startGame() throws GameException {
		User creator = userDetailsService.getPrincipal();
		        
        Game game = gameInitService.getCreatedGame(creator);
        if (game == null)
        {
        	throw new GameException(511, "Game not found! User " + creator + " has not created any game"); 
        }
        
        gameService.startGame(game);
        
        return true;
		
	}		
	
	@RequestMapping(value="/api/game/host", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public boolean checkHost() throws GameException {
		User player = userDetailsService.getPrincipal();
		        
        Game game = gameInitService.getCreatedGame(player);
        if (game == null)
        {
        	game = gameService.getGameByPlayer(player);
        	//game = gameOpponentRepository.getGameByPlayer(player);
        	if (game == null)
	        {
	        	throw new GameException(512, "Game not found! User " + player + " is not playing any game"); 
	        }
        }
       		        
        if (game.getRound().getHost().getId() == player.getId())
        {
        	return true;
        }
        else
        {
        	return false;
        }
		
	}		
	

	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		logger.error("Game Controller: Game Exception");
		logger.error(ge.toString());
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());
		return re;
   }
}
