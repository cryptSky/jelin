package org.crama.jelin.controller;

import java.util.ArrayList;
import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Constants.GameState;
import org.crama.jelin.model.Constants.NetStatus;
import org.crama.jelin.model.Constants.Readiness;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.Question;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.ScoreSummary;
import org.crama.jelin.model.User;
import org.crama.jelin.service.CategoryService;
import org.crama.jelin.service.GameInitService;
import org.crama.jelin.service.GameService;
import org.crama.jelin.service.UserService;
import org.crama.jelin.service.UserStatisticsService;
import org.hibernate.Hibernate;
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
public class GameController {

	private static final Logger logger = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GameInitService gameInitService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserStatisticsService userStatisticsService;
	
	
	@RequestMapping(value="/api/game/start", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public boolean startGame() throws GameException {
		User creator = userService.getPrincipal();
		        
        Game game = gameInitService.getCreatedGame(creator);
        if (game == null)
        {
        	throw new GameException(511, "Game not found! User " + creator.getUsername() + " has not created any game"); 
        }
       
        gameService.startGame(game);
                
        return true;
		
	}		
	
	@RequestMapping(value="/api/game/host", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public boolean checkHost() throws GameException {
		User player = userService.getPrincipal();
		if (player.getNetStatus() == NetStatus.OFFLINE)
        {
        	throw new GameException(512, "Your user is OFFLINE. Please, check Readiness to make it online.");
        }
		
        Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
        if (game == null)
        {
        	game = gameService.getGameByPlayer(player);
        	if (game == null)
	        {
	        	throw new GameException(512, "Game not found! User " + player.getUsername() + " is not playing any game"); 
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
	
	@RequestMapping(value="/api/game/readiness", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public String getReadiness() throws GameException {
		
		User player = userService.getPrincipal();
		Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
		
        if (game == null) {
        	game = gameService.getGameByPlayer(player);
        	
        	if (game == null) {
	        	throw new GameException(513, "Game not found! User " + player.getUsername() + " is not playing any game"); 
	        }
        }
        
        if (!game.getGameState().equals(GameState.IN_PROGRESS)) {
        	throw new GameException(513, "Game is not in progress. Current game state: " + game.getGameState().toString());
        }
        
        if (player.getNetStatus() == NetStatus.OFFLINE)
		{
        	player.setNetStatus(NetStatus.ONLINE);
			userService.changeNetStatus(player, NetStatus.ONLINE.getValue()); 
			player.setCurrentPlayerReadiness(game.getReadiness());
			logger.info("User " + player.getUsername() + " is online again");
		}
        
        return game.getReadiness().toString();
        
	}
	
	@RequestMapping(value="/api/game/categories", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Category> getGameCategories() throws GameException {
		User player = userService.getPrincipal();
		if (player.getNetStatus() == NetStatus.OFFLINE)
        {
        	throw new GameException(512, "Your user is OFFLINE. Please, check Readiness to make it online.");
        }
		
		Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
		
        if (game == null) {
        	game = gameService.getGameByPlayer(player);
        	
        	if (game == null) {
	        	throw new GameException(514, "Game not found! User " + player.getUsername() + " is not playing any game"); 
	        }
        }
        
             
        //check if player is host
        if (game.getRound().getHost().getId() != player.getId()) {
        	throw new GameException(514, "User is not a host for current round");
        }
        //check readiness is CATEGORY
        if (!game.getReadiness().equals(Readiness.CATEGORY)) {
        	throw new GameException(514, "Game Readiness is: " + game.getReadiness().toString() + ". Should be: CATEGORY");
        }
        
        Category theme = game.getTheme();
	    List<Category> categories = categoryService.getAllCategoriesFromThemes(theme.getId());
	    if (categories.size() == 1) {
	    	gameService.saveRoundCategory(game, categories.get(0));
	    	
	       	return new ArrayList<Category>();
	    }
	    return categories;

	}
	
	  
	@RequestMapping(value="/api/game/category", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void saveRoundCategory(@RequestParam(required = false) Integer category) throws GameException {
		User player = userService.getPrincipal();
		if (player.getNetStatus() == NetStatus.OFFLINE)
        {
        	throw new GameException(512, "Your user is OFFLINE. Please, check Readiness to make it online.");
        }
		
		Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
		
        if (game == null) {
        	game = gameService.getGameByPlayer(player);
        	
        	if (game == null) {
	        	throw new GameException(515, "Game not found! User " + player.getUsername() + " is not playing any game"); 
	        }
        }
        //check if player is host
        if (game.getRound().getHost().getId() != player.getId()) {
        	throw new GameException(515, "User is not a host for current round");
        }
        //check readiness is CATEGORY
        if (!game.getReadiness().equals(Readiness.CATEGORY)) {
        	throw new GameException(515, "Game Readiness is: " + game.getReadiness().toString() + ". Should be: CATEGORY");
        }
        
        //choose random category
        if (category == null) {
        	gameService.setRandomCategory(game);
        	
        }
        else { 
	        Category categoryObj = categoryService.getCategoryById(category);
	        
	        if (categoryObj == null) {
	        	throw new GameException(515, "Category with given id is not exist");
	        }
	        
	        gameService.saveRoundCategory(game, categoryObj);
        }
        
	}

	
	
	@RequestMapping(value="/api/game/question", method=RequestMethod.GET, 
			produces={"application/json; charset=UTF-8"})
	public @ResponseBody Question getNextQuestion() throws GameException {
		User player = userService.getPrincipal();
		if (player.getNetStatus() == NetStatus.OFFLINE)
        {
        	throw new GameException(512, "Your user is OFFLINE. Please, check Readiness to make it online.");
        }
		
        Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
        if (game == null)
        {
        	game = gameService.getGameByPlayer(player);
        	if (game == null)
	        {
	        	throw new GameException(516, "Game not found! User " + player.getUsername() + " is not playing any game"); 
	        }
        }
		
        if (game.getReadiness() != Readiness.QUESTION)
        {
        	throw new GameException(516, "Game Readiness is: " + game.getReadiness().toString() + ". Should be: QUESTION");
        }
        
        if (player.getReadiness() == Readiness.QUESTION)
        {
        	throw new GameException(516, "User already got his new question");
        }
        
           
        Question question = gameService.processQuestion(game, player);
            	
        return question;
	}
	
	@RequestMapping(value="/api/game/answer", method=RequestMethod.POST)
	public void answer(@RequestParam int variant, @RequestParam int time) throws GameException {
		User player = userService.getPrincipal();
		if (player.getNetStatus() == NetStatus.OFFLINE)
        {
        	throw new GameException(512, "Your user is OFFLINE. Please, check Readiness to make it online.");
        }
		
		Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
        if (game == null)
        {
        	game = gameService.getGameByPlayer(player);
        	if (game == null)
	        {
	        	throw new GameException(517, "Game not found! User " + player.getUsername() + " is not playing any game"); 
	        }
        }
		
        if (game.getReadiness() != Readiness.ANSWER)
        {
        	throw new GameException(517, "Game Readiness is: " + game.getReadiness().toString() + ". Should be: ANSWER");
        }
        
        if (player.getReadiness() == Readiness.ANSWER)
        {
        	throw new GameException(516, "User has already answered.");
        }
        
        gameService.processAnswer(game, player, variant, time);
      
	}
	
	@RequestMapping(value="/api/game/results", method=RequestMethod.POST, 
			produces={"application/json; charset=UTF-8"})
	public @ResponseBody List<QuestionResult> getQuestionResult() throws GameException {
		User player = userService.getPrincipal();
		if (player.getNetStatus() == NetStatus.OFFLINE)
        {
        	throw new GameException(512, "Your user is OFFLINE. Please, check Readiness to make it online.");
        }
		
		Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
        if (game == null)
        {
        	game = gameService.getGameByPlayer(player);
        	if (game == null)
	        {
	        	throw new GameException(518, "Game not found! User  " + player.getUsername() + " is not playing any game"); 
	        }
        }
		
        if (game.getReadiness() != Readiness.RESULT)
        {
        	throw new GameException(518, "Game Readiness is: " + game.getReadiness().toString() + ". Should be: RESULT");
        }
        
        List<QuestionResult>  result = gameService.processResult(game, player);

        return result;
	}
	
	@RequestMapping(value="/api/game/summary", method=RequestMethod.POST)
	public @ResponseBody List<ScoreSummary> getScoreSummary() throws GameException {
		User player = userService.getPrincipal();
		if (player.getNetStatus() == NetStatus.OFFLINE)
        {
        	throw new GameException(512, "Your user is OFFLINE. Please, check Readiness to make it online.");
        }
		
		Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
        if (game == null)
        {
        	game = gameService.getGameByPlayer(player);
        	if (game == null)
	        {
	        	throw new GameException(519, "Game not found! User " + player.getUsername() + " is not playing any game"); 
	        }
        }
		
        if (game.getReadiness() != Readiness.SUMMARY && game.getReadiness() != Readiness.RESULT)
        {
        	throw new GameException(519, "Game Readiness is: " + game.getReadiness().toString() + ". Should be: SUMMARY or RESULT");
        }
        
        List<ScoreSummary> summaries = new ArrayList<ScoreSummary>();
        if (game.getReadiness() == Readiness.SUMMARY) {
        	summaries = gameService.getScoreSummary(game, player);
            userStatisticsService.saveGameSummaryStats(summaries);
        }
                
        else if (game.getReadiness() == Readiness.RESULT) {
        	summaries = gameService.getScoreSummaryAfterRound(game, player);
        }
        
        
        return summaries;
	}
	
	@RequestMapping(value="/api/game/close", method=RequestMethod.POST)
	public void closeGame() throws GameException {
		User creator = userService.getPrincipal();
		Game game = gameInitService.getGame(creator, null);
		
		gameInitService.checkGameCreated(game);
		
		gameInitService.closeGame(game);
	}
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		logger.error(ge.toString());
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());
		return re;
   }
	
	
	//TEST METHODS
	@RequestMapping(value="/api/game/clean", method=RequestMethod.POST)
	public void cleanUpGame(@RequestParam int id) throws GameException {
		
		Game game = gameService.getGameById(id);
		
		gameService.cleanUpGame(game);
	}
	
}
