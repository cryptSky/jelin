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
import org.crama.jelin.model.json.UserJson;
import org.crama.jelin.service.CategoryService;
import org.crama.jelin.service.DifficultyService;
import org.crama.jelin.service.GameInitService;
import org.crama.jelin.service.OpponentSearchService;
import org.crama.jelin.service.UserActivityService;
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
	private OpponentSearchService opponentSearchService;

	@Autowired
	private UserActivityService userActivityService;
	
	@RequestMapping(value="/api/game", method=RequestMethod.PUT, params={"theme", "random"})
	@ResponseStatus(HttpStatus.CREATED)
    public boolean setTheme(@RequestParam int theme, @RequestParam(required = false) boolean random) throws GameException {
		   
        User creator = userService.getPrincipal();
        
        Category category = categoryService.getThemeById(theme);
        
        //user is not null
        userService.checkUserAuthorized(creator);
        //category is not null
        categoryService.checkCategoryNotNull(category);
        if (!userService.checkUserStatusIsEquals(creator, ProcessStatus.FREE) && 
        		!creator.getProcessStatus().equals(ProcessStatus.CALLING)) {
        	//user is in process status free 
	        throw new GameException(102, "User should be in status " + ProcessStatus.FREE + " or in status " + ProcessStatus.CALLING 
	        		+ " to call this method. Current status: " + creator.getProcessStatus());
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
		User creator = userService.getPrincipal();
		    
        //user is not null
        userService.checkUserAuthorized(creator);
        //difficulty is not null
        difficultyService.checkDifficultyNotNull(diff);
        //user is in process status free 
        if (!userService.checkUserStatusIsEquals(creator, ProcessStatus.FREE) && 
        		!creator.getProcessStatus().equals(ProcessStatus.CALLING)) {
        	//user is in process status free 
	        throw new GameException(102, "User should be in status " + ProcessStatus.FREE + " or in status " + ProcessStatus.CALLING 
	        		+ " to call this method. Current status: " + creator.getProcessStatus());
        }
        
        
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
	public Set<UserJson> getOpponents() throws GameException {
		
		User user = userService.getPrincipal();
		Game game = gameInitService.getGame(user, null);
		
		if (game == null) {
        	game = gameInitService.getInviteGame(user);
        	
        	if (game == null) {
	        	throw new GameException(515, "Game not found! User " + user.getUsername() + " "
	        			+ "doesn't created and is not invited to any game"); 
	        }
        }
		
		System.out.println(game);
		System.out.println(user);
		
		if (user.getChoosenCharacter() == null) {
			throw new GameException(501, "Character is not set");
		}
		gameInitService.checkGameCreated(game);
		if (game.getTheme() == null) {
			throw new GameException(406, "Game theme is not set" );
		}
		
		difficultyService.checkDifficultyNotNull(game.getDifficulty());
		gameInitService.checkGameRandom(game);
		
		
		Set<UserJson> opponents = gameInitService.getGameOpponents(game);
		return opponents;
	}
	
	@RequestMapping(value = "/api/game/opponents/kick", method = RequestMethod.POST)
	public void removeOpponent(@RequestParam int user) throws GameException {
		
		User creator = userService.getPrincipal();
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

		User creator = userService.getPrincipal();		
		return creator.getProcessStatus().toString();

	}
	
	@RequestMapping(value = "/api/game/invite", method = RequestMethod.POST, params={"user"})
	public @ResponseBody String inviteUser(@RequestParam int user) throws GameException {
		
		User creator = userService.getPrincipal();
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
		//check if opponent exist
		if (opponent == null) {
			throw new GameException(103, "There is no user with id: " + opponent);
		}
		//5. check opponent net status is online or shadow
		if (opponent.getNetStatus().equals(NetStatus.OFFLINE)) {
			throw new GameException(103, "Opponent is OFFLINE");

		}
		//6. check opponent process status is free
		if (!opponent.getProcessStatus().equals(ProcessStatus.FREE)) {
			throw new GameException(104, "Opponent should be in status " + ProcessStatus.FREE 
	    			+ "Current status: " + opponent.getProcessStatus());
		}
		
		//check opponent last invite reject time
		if (!gameInitService.checkLastRejectTime(opponent)) {
			throw new GameException(510, "There is not enough time passed since the last invite was rejected by this opponent");
		}
		
		//invite user
		InviteStatus inviteStatus = gameInitService.inviteUser(game, creator, opponent, false);
		User opponentAfterInvite = userService.getUser(user);
		userActivityService.saveInvite(opponentAfterInvite, inviteStatus);
		return inviteStatus.toString();

	}
	
	
	@RequestMapping(value = "/api/game/invite/wait", method = RequestMethod.GET)
	public boolean checkInviteStatus() throws GameException {
		User user = userService.getPrincipal();
		Game game = gameInitService.getCreatedGame(user);
		
		gameInitService.checkGameCreated(game);
		
		return gameInitService.checkInviteStatus(game);
		
	}
	
	
	
	@RequestMapping(value = "/api/game/invite", method = RequestMethod.GET)
	public @ResponseBody Game getInvite() throws GameException {
		User user = userService.getPrincipal();
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
		User user = userService.getPrincipal();
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
		User user = userService.getPrincipal();
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

		User creator = userService.getPrincipal();
		Game game = gameInitService.getCreatedGame(creator);
		
		if (game.getGameOpponents().size() == 3)
		{
			throw new GameException(501, "The game has no more available seats!");
		}
		
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
			InviteStatus inviteStatus = gameInitService.inviteUser(game, creator, opponent, true);			
			return inviteStatus.toString();
		}				
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
