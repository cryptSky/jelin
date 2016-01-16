package org.crama.jelin.controller;

import java.util.ArrayList;
import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Constants;
import org.crama.jelin.model.Constants.GameState;
import org.crama.jelin.model.Constants.Readiness;
import org.crama.jelin.model.Constants.UserType;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.Question;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.ScoreSummary;
import org.crama.jelin.model.User;
import org.crama.jelin.service.CategoryService;
import org.crama.jelin.service.GameInitService;
import org.crama.jelin.service.GameService;
import org.crama.jelin.service.UserDetailsServiceImpl;
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
		        
        Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
        if (game == null)
        {
        	game = gameService.getGameByPlayer(player);
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
	
	@RequestMapping(value="/api/game/readiness", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public String getReadiness() throws GameException {
		
		User player = userDetailsService.getPrincipal();
		
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
        
        return game.getReadiness().toString();
        
	}
	
	@RequestMapping(value="/api/game/categories", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Category> getGameCategories() throws GameException {
		User player = userDetailsService.getPrincipal();
		
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
        
        /*//if player is bot
        if (player.getType().equals(UserType.BOT)) {
        	
        }
        else {*/
	        Category theme = game.getTheme();
	        List<Category> categories = categoryService.getAllCategoriesFromThemes(theme.getId());
	        if (categories.size() == 1) {
	        	gameService.saveRoundCategory(game, categories.get(0));
	        	return new ArrayList<Category>();
	        }
	        return categories;
        //}
	}
	
	@RequestMapping(value="/api/game/category", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void saveRoundCategory(@RequestParam int category) throws GameException {
		User player = userDetailsService.getPrincipal();
		
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
        
        Category categoryObj = categoryService.getCategoryById(category);
        
        gameService.saveRoundCategory(game, categoryObj);
        
	}

	
	
	@RequestMapping(value="/api/game/question", method=RequestMethod.GET)
	public @ResponseBody Question getNextQuestion() throws GameException {
		User player = userDetailsService.getPrincipal();
        
        Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
        if (game == null)
        {
        	game = gameService.getGameByPlayer(player);
        	if (game == null)
	        {
	        	throw new GameException(516, "Game not found! User " + player + " is not playing any game"); 
	        }
        }
		
        if (game.getReadiness() != Readiness.QUESTION)
        {
        	throw new GameException(516, "Readiness is not equal to QUESTION!");
        }
        
        GameRound round = game.getRound();
        if (round.alreadyGotQuestion(player))
        {
        	throw new GameException(516, "User already got his new question in this round but other players haven't got it yet");
        }
        
        
        Question question = gameService.getNextQuestion(game, player);
        if (question == null)
        {
        	throw new GameException(516, "There is no next question in this round!");
        }
        
        gameService.setPlayerQuestion(round, player);
        
        if (round.allHumanGotQuestion())
        {
        	game.setReadiness(Readiness.ANSWER);
        	gameService.updateGame(game);
        }
            	
        return question;
	}
	
	@RequestMapping(value="/api/game/answer", method=RequestMethod.POST)
	public void answer(@RequestParam int variant, @RequestParam int time) throws GameException {
		User player = userDetailsService.getPrincipal();
        
		Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
        if (game == null)
        {
        	game = gameService.getGameByPlayer(player);
        	if (game == null)
	        {
	        	throw new GameException(517, "Game not found! User " + player + " is not playing any game"); 
	        }
        }
		
        if (game.getReadiness() != Readiness.ANSWER)
        {
        	throw new GameException(517, "Readiness is not equal to ANSWER!");
        }
        
        gameService.processAnswer(game, player, variant, time);
        
        GameRound round = game.getRound();
        
        // if all Humans already answered
        if (game.getHumanPlayersCount() == round.getHumanAnswerCount())
        {
        	
        	round.setHumanAnswerCount(0);
        	gameService.updateGameRound(round);
        	gameService.updateGame(game);
        	
        	gameService.processBotsAnswers(game);
        	
        	// end of round
        	if (game.getRound().endOfRound())
        	{
        		gameService.finishRound(game.getRound());
        		game.setReadiness(Readiness.RESULT);       // RESULT state never is available
            	gameService.updateGame(game);
            	
            	boolean hasNextRound = gameService.nextRound(game);
            	if (!hasNextRound)
            	{       	
            		game.setReadiness(Readiness.SUMMARY);
                	gameService.updateGame(game);
                	
            		gameService.finishGame(game);
            		return;
            	}
            	
            	// if next round host is bot, set category
            	if (game.getRound().getHost().getType() == UserType.BOT)
            	{
            		gameService.setRandomCategory(game);
              	}
            	else
            	{
            		game.setReadiness(Readiness.CATEGORY);
                	gameService.updateGame(game);
            	}
        	}
        	else
        	{
        		game.setReadiness(Readiness.QUESTION);
            	gameService.updateGame(game);
        	}
        	        	     	
        }     	
	}
	
	@RequestMapping(value="/api/game/results", method=RequestMethod.POST)
	public @ResponseBody List<QuestionResult> getQuestionResult() throws GameException {
		User player = userDetailsService.getPrincipal();
        
		Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
        if (game == null)
        {
        	game = gameService.getGameByPlayer(player);
        	if (game == null)
	        {
	        	throw new GameException(518, "Game not found! User  " + player + " is not playing any game"); 
	        }
        }
		
        if (game.getReadiness() != Readiness.RESULT)
        {
        	throw new GameException(518, "Readiness is not equal to RESULT!");
        }
		
        List<QuestionResult> result = gameService.getPersonalRoundResults(game, player);
        
        return result;
	}
	
	@RequestMapping(value="/api/game/summary", method=RequestMethod.POST)
	public @ResponseBody List<ScoreSummary> getScoreSummary() throws GameException {
		User player = userDetailsService.getPrincipal();
        
		Game game = gameInitService.getGame(player, GameState.IN_PROGRESS);
        if (game == null)
        {
        	game = gameService.getGameByPlayer(player);
        	if (game == null)
	        {
	        	throw new GameException(519, "Game not found! User " + player + " is not playing any game"); 
	        }
        }
		
        if (game.getReadiness() != Readiness.SUMMARY && game.getReadiness() != Readiness.RESULT)
        {
        	throw new GameException(519, "Readiness is not equal to SUMMARY!");
        }
        
        List<ScoreSummary> summaries = gameService.getScoreSummary(game);
        
        return summaries;
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
