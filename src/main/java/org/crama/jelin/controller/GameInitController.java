package org.crama.jelin.controller;

import java.util.Set;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Constants;
import org.crama.jelin.model.Constants.GameState;
import org.crama.jelin.model.Constants.InviteStatus;
import org.crama.jelin.model.Constants.NetStatus;
import org.crama.jelin.model.Constants.ProcessStatus;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.User;
import org.crama.jelin.service.CategoryService;
import org.crama.jelin.service.DifficultyService;
import org.crama.jelin.service.GameInitService;
import org.crama.jelin.service.OpponentSearchService;
import org.crama.jelin.service.UserDetailsServiceImpl;
import org.crama.jelin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameInitController {

	private static final Logger logger = LoggerFactory.getLogger(GameInitController.class);
	
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
	@Autowired
	private OpponentSearchService opponentSearchService;
	
	@RequestMapping(value="/api/game", method=RequestMethod.PUT, params={"theme", "random"})
	@ResponseStatus(HttpStatus.CREATED)
    public boolean setTheme(@RequestParam int theme, @RequestParam(required = false) boolean random) throws GameException {
		   
        User creator = userDetailsService.getPrincipal();
        
        Category category = categoryService.getThemeById(theme);
        
        //user is not null
        userService.checkUserAuthorized(creator);
        //category is not null
        categoryService.checkCategoryNotNull(category);
        if (!userService.checkUserStatusIsEquals(creator, ProcessStatus.FREE) && 
        		!creator.getProcessStatus().equals(ProcessStatus.CALLING)) {
        	//user is in process status free 
	        throw new GameException(102, "User should be in status " + ProcessStatus.FREE + " or in status " + ProcessStatus.CALLING 
	        		+ " to call this method. Current status: " + creator.getProcessStatus().getValue());
        }
        
        Game game = gameInitService.getCreatedGame(creator);
        if (game == null) {
        	// new game
        	if (gameInitService.initGame(creator, category, random)) {
        		userService.updateUserProcessStatus(creator, ProcessStatus.CALLING);
        		return true;
        	}
        	else {
        		return false;
           	}	
        }
        else {
        	//game already exists
        	gameInitService.updateCategory(game, category, random);
        	return true;
        }
        
	}		

	@RequestMapping(value="/api/game", method=RequestMethod.POST, params={"difficulty"})
	@ResponseStatus(HttpStatus.OK)
    public boolean updateDifficulty(@RequestParam int difficulty) throws GameException {
		Difficulty diff = difficultyService.getDifficultyById(difficulty);
		User creator = userDetailsService.getPrincipal();
		    
        //user is not null
        userService.checkUserAuthorized(creator);
        //difficulty is not null
        difficultyService.checkDifficultyNotNull(diff);
        //user is in process status free 
        if (!userService.checkUserStatusIsEquals(creator, ProcessStatus.FREE) && 
        		!creator.getProcessStatus().equals(ProcessStatus.CALLING)) {
        	//user is in process status free 
	        throw new GameException(102, "User should be in status " + ProcessStatus.FREE + " or in status " + ProcessStatus.CALLING 
	        		+ " to call this method. Current status: " + creator.getProcessStatus().getValue());
        }
        
        /*else if (!creator.getProcessStatus().equals(ProcessStatus.CALLING)) {
        	System.out.println("User is in status: " + creator.getProcessStatus() + ". User should be in status calling.");
        	return false;
        	
        }*/
        
        Game game = gameInitService.getCreatedGame(creator);
        
        if (game == null) {
        	gameInitService.initGame(creator, diff);
        	userService.updateUserProcessStatus(creator, ProcessStatus.CALLING);
        	return true;
        }
        else {
        	return gameInitService.updateDifficulty(game, diff);
	        
        }
	}
	
	@RequestMapping(value = "/api/game/opponents", method = RequestMethod.GET)
	public Set<User> getOpponents() throws GameException {
		
		User creator = userDetailsService.getPrincipal();
		Game game = gameInitService.getCreatedGame(creator);
		
		System.out.println(game);
		System.out.println(creator);
		
		if (creator.getChoosenCharacter() == null) {
			throw new GameException(501, "Character is not set");
		}
		if (game.getTheme() == null) {
			throw new GameException(406, "Game theme is not set" );
		}
		gameInitService.checkGameCreated(game);
		difficultyService.checkDifficultyNotNull(game.getDifficulty());
		gameInitService.checkGameRandom(game);
		
		
		if (!userService.checkUserStatusIsEquals(creator, ProcessStatus.CALLING)) {
        	//user is in process status free 
	        throw new GameException(102, "User should be in status " + ProcessStatus.CALLING + " to get opponents. "
	    			+ "User is in status: " + creator.getProcessStatus());
        }
		
		Set<User> opponents = gameInitService.getGameOpponents(game);
		return opponents;
	}
	
	@RequestMapping(value = "/api/game/opponents/kick", method = RequestMethod.POST)
	public void removeOpponent(@RequestParam int user) throws GameException {
		
		User creator = userDetailsService.getPrincipal();
		Game game = gameInitService.getCreatedGame(creator);
		
		System.out.println(creator);
		System.out.println(game);
		
		if (creator.getChoosenCharacter() == null) {
			throw new GameException(501, "Character is not set");
		}
		gameInitService.checkGameCreated(game);
		difficultyService.checkDifficultyNotNull(game.getDifficulty());
		gameInitService.checkGameRandom(game);
		if (game.getTheme() == null) {
			throw new GameException(406, "Game theme is not set" );
		}
		if (!userService.checkUserStatusIsEquals(creator, ProcessStatus.CALLING)) {
        	//user is in process status free 
	        throw new GameException(102, "User should be in status " + ProcessStatus.CALLING + " to kick opponent. "
	    			+ "User is in status: " + creator.getProcessStatus());
        }
		//check if user is in opponent set and remove it
		gameInitService.removeOpponent(game, user);
		
	}
	@RequestMapping(value = "/api/game/userGameState", method = RequestMethod.GET)
	public @ResponseBody String getUserGameStatus() {
		User creator = userDetailsService.getPrincipal();
		return Constants.ProcessStatusString[creator.getProcessStatus().getValue()];
	}
	
	@RequestMapping(value = "/api/game/invite", method = RequestMethod.POST, params={"user"})
	public @ResponseBody String inviteUser(@RequestParam int user) throws GameException {
		
		User creator = userDetailsService.getPrincipal();
		Game game = gameInitService.getCreatedGame(creator);
		
		if (creator.getChoosenCharacter() == null) {
			throw new GameException(501, "Character is not set");
		}
		gameInitService.checkGameCreated(game);
		difficultyService.checkDifficultyNotNull(game.getDifficulty());
		gameInitService.checkGameRandom(game);
		if (game.getTheme() == null) {
			throw new GameException(406, "Game theme is not set" );
		}
		if (!userService.checkUserStatusIsEquals(creator, ProcessStatus.CALLING)) {
        	//user is in process status free 
	        throw new GameException(102, "User should be in status " + ProcessStatus.CALLING + " to get invite opponents. "
	    			+ "User is in status: " + creator.getProcessStatus());
        }
		
		gameInitService.checkNumOfOpponents(game);
		
		User opponent = userService.getUser(user);
		//5. check opponent net status is online or shadow
		if (opponent.getNetStatus().equals(NetStatus.OFFLINE)) {
			throw new GameException(103, "Opponent is OFFLINE");

		}
		//6. check opponent process status is free
		if (!opponent.getProcessStatus().equals(ProcessStatus.FREE)) {
			throw new GameException(104, "Opponent should be in status " + ProcessStatus.FREE 
	    			+ "Current status: " + opponent.getProcessStatus());
		}
		//7 check if user already inviting someone
		/*if (gameInitService.checkInviteStatus(game)) {
			System.out.println("User already inviting opponent");
			return null;
		}*/
		//invite user
		InviteStatus inviteStatus = gameInitService.inviteUser(game, creator, opponent);
		return Constants.InviteStatusString[inviteStatus.getValue()];
	}
	
	
	@RequestMapping(value = "/api/game/invite/wait", method = RequestMethod.GET)
	public boolean checkInviteStatus() throws GameException {
		User user = userDetailsService.getPrincipal();
		Game game = gameInitService.getCreatedGame(user);
		
		gameInitService.checkGameCreated(game);
		
		return gameInitService.checkInviteStatus(game);
		
	}
	
	
	
	@RequestMapping(value = "/api/game/invite", method = RequestMethod.GET)
	public @ResponseBody Game getInvite() throws GameException {
		User user = userDetailsService.getPrincipal();
		System.out.println(user.getUsername());
		//1. check if user state is inviting
		if (!user.getProcessStatus().equals(ProcessStatus.INVITING)) {
			throw new GameException(102, "User should be in status " + ProcessStatus.INVITING + " to get invitation. "
	    			+ "User is in status: " + user.getProcessStatus());
			
		}
		
		//get invite game
		Game game = gameInitService.getInviteGame(user);
		return game;
	}
	
	@RequestMapping(value = "/api/game/invite/confirm", method = RequestMethod.POST)
	public void confirmInvite() throws GameException {
		User user = userDetailsService.getPrincipal();
		Game game = gameInitService.getInviteGame(user);
	
		
		if (!user.getProcessStatus().equals(ProcessStatus.INVITING)) {
			throw new GameException(102, "User should be in status " + ProcessStatus.INVITING + " to accept invitation. "
    			+ "User is in status: " + user.getProcessStatus());
		}
		
		gameInitService.checkGameCreated(game);
		difficultyService.checkDifficultyNotNull(game.getDifficulty());
		gameInitService.checkGameRandom(game);
		if (game.getTheme() == null) {
			throw new GameException(406, "Game theme is not set" );
		}
	
		//4. check if game state is created
		if (!game.getGameState().equals(GameState.CREATED)) {
			throw new GameException(403, "Game is not in state CREATED");
		}
		gameInitService.checkNumOfOpponents(game);
		
		gameInitService.confirmInvite(game, user);
		
	}
	
	
	@RequestMapping(value = "/api/game/invite/refuse", method = RequestMethod.POST)
	public void refuseInvite() throws GameException {
		User user = userDetailsService.getPrincipal();
		Game game = gameInitService.getInviteGame(user);
		
		//1. check if user state is inviting
		if (!user.getProcessStatus().equals(ProcessStatus.INVITING)) {
			throw new GameException(102, "User should be in status " + ProcessStatus.INVITING + " to refuse invitation. "
	    			+ "User is in status: " + user.getProcessStatus());
			
		}
		
		gameInitService.checkGameCreated(game);
		difficultyService.checkDifficultyNotNull(game.getDifficulty());
		gameInitService.checkGameRandom(game);
		if (game.getTheme() == null) {
			throw new GameException(406, "Game theme is not set" );
		}
		//check if game state is created
		if (!game.getGameState().equals(GameState.CREATED)) {
			throw new GameException(403, "Game is not in state CREATED");
		}
		gameInitService.refuseInvite(game, user);
		
	}
	

	@RequestMapping(value="/api/game/invite", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)

	public @ResponseBody String inviteRandomUser() throws GameException {

		User creator = userDetailsService.getPrincipal();
		Game game = gameInitService.getCreatedGame(creator);
		
		if (creator.getChoosenCharacter() == null) {
			throw new GameException(501, "Character is not set");
		}
		
		gameInitService.checkGameCreated(game);
		difficultyService.checkDifficultyNotNull(game.getDifficulty());
		if (game.getTheme() == null) {
			throw new GameException(406, "Game theme is not set" );
		}
		//3. check if game is random
		if (!game.getRandom()) {
			throw new GameException(404, "Game is not random");
		}
				
		//4. check if user state is calling
		if (!creator.getProcessStatus().equals(ProcessStatus.CALLING)) {
			 throw new GameException(102, "User should be in status " + ProcessStatus.CALLING
		    			+ "Current status: " + creator.getProcessStatus());
		}
		
		User opponent = opponentSearchService.findOpponent(game);
		if (opponent == null) {
			opponent = opponentSearchService.createBot(game);
			return null;
		}

		else {
			//invite user
			InviteStatus inviteStatus = gameInitService.inviteUser(game, creator, opponent);
			return Constants.InviteStatusString[inviteStatus.getValue()];
		}				
	}
	
	@RequestMapping(value="/api/game/close", method=RequestMethod.POST)
	public void closeGame() throws GameException {
		User creator = userDetailsService.getPrincipal();
		Game game = gameInitService.getCreatedGame(creator);
		
		gameInitService.checkGameCreated(game);
		
		gameInitService.closeGame(game);
	}
	
	//EXCEPTION HANLER
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		System.out.println("Game Init Controller: Game Exception");
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());	
        return re;
    }
	
	
	
	
}
