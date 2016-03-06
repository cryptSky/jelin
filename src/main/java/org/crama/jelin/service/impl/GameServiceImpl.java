package org.crama.jelin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Answer;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Constants.GameState;
import org.crama.jelin.model.Constants.InviteStatus;
import org.crama.jelin.model.Constants.NetStatus;
import org.crama.jelin.model.Constants.ProcessStatus;
import org.crama.jelin.model.Constants.Readiness;
import org.crama.jelin.model.Constants.UserType;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameBot;
import org.crama.jelin.model.GameOpponent;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.Question;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.ScoreSummary;
import org.crama.jelin.model.Settings;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.AnswerRepository;
import org.crama.jelin.repository.GameOpponentRepository;
import org.crama.jelin.repository.GameRepository;
import org.crama.jelin.repository.GameRoundRepository;
import org.crama.jelin.repository.QuestionResultRepository;
import org.crama.jelin.repository.ScoreSummaryRepository;
import org.crama.jelin.repository.UserInterestsRepository;
import org.crama.jelin.repository.UserRepository;
import org.crama.jelin.service.CategoryService;
import org.crama.jelin.service.GameBotService;
import org.crama.jelin.service.GameService;
import org.crama.jelin.service.OfflinePlayerChecker;
import org.crama.jelin.service.PointsCalculatorService;
import org.crama.jelin.service.QuestionService;
import org.crama.jelin.service.SettingsService;
import org.crama.jelin.service.UserActivityService;
import org.crama.jelin.service.UserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("gameService")
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GameRoundRepository gameRoundRepository;
	
	@Autowired
	private GameOpponentRepository gameOpponentRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private GameBotService gameBotService;
	
	@Autowired 
	private QuestionResultRepository questionResultRepository;
	
	@Autowired
	private PointsCalculatorService pointsCalculatorService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ScoreSummaryRepository scoreSummaryRepository;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private UserStatisticsService userStatisticsService;
	
	@Autowired
	private OfflinePlayerChecker offlinePlayerChecker;
	
	@Autowired 
	private UserInterestsRepository userInterestsRepository; 

	@Autowired
	private SettingsService settingsService;

	
	@Override
	@Transactional
	public void startGame(Game game) throws GameException {
		List<User> hosts = setUpHosts(game);
						
		List<GameRound> gameRounds = new ArrayList<GameRound>();

		Settings settings = settingsService.getSettings();
		for (int round = 0; round < settings.getRoundNumber(); round++)
		{
			GameRound gameRound = new GameRound(game, round, hosts.get(round));
			gameRounds.add(gameRound);
		}
		
		game.setRound(gameRounds.get(0));
		game.setGameState(GameState.IN_PROGRESS);
		gameRoundRepository.saveOrUpdateRounds(gameRounds);
		
		setReadiness(game, Readiness.CATEGORY);
				
		if (hosts.get(0).getType() == UserType.BOT)
		{
			setRandomCategory(game);			
		}
		
		gameRoundRepository.saveOrUpdateRounds(gameRounds);
		updateGame(game);
		
		User creator = game.getCreator();
		creator.setProcessStatus(ProcessStatus.INGAME);
		creator.setReadiness(Readiness.CATEGORY);
		userRepository.updateUser(creator);
		
		userActivityService.saveUserGameActivity(creator);
		userStatisticsService.addInitiatedGame(creator);
		
		for (GameOpponent opponent: game.getGameOpponents())
		{
			if (opponent.getInviteStatus().equals(InviteStatus.ACCEPTED)) {
				User player = opponent.getUser();
				player.setProcessStatus(ProcessStatus.INGAME);
				
				player.setReadiness(Readiness.CATEGORY);
				
				userRepository.updateUser(player);
				
				userActivityService.saveUserGameActivity(player);
			}
		}
		
		offlinePlayerChecker.setUpTimeout(game, Readiness.CATEGORY);	
				
	}
	
	@Override
	@Transactional
	public Question processQuestion(Game game, User user) throws GameException {
		
		boolean callerIsOnline = user.getNetStatus() != NetStatus.OFFLINE;
		if (!callerIsOnline)
		{
			user = userRepository.getUser(user.getId());
			game = gameRepository.reloadGame(game);
			userRepository.lock(user);
			gameRepository.lock(game);
			
		}
			
		Question question = getNextQuestion(game, user);
        if (question == null)
        {
        	throw new GameException(516, "There is no next question in this round!");
        }
        	
        user.setReadiness(Readiness.QUESTION);
        userRepository.updateUser(user);
        updateGame(game);
       
        if ((callerIsOnline && game.allActiveHasReadiness(Readiness.QUESTION)) ||
				(!callerIsOnline && game.allHasReadiness(Readiness.QUESTION)))
        {
			if (callerIsOnline)
			{
				for (User player: game.getOfflinePlayers())
				{
					getNextQuestion(game, player);			
				}
			}
			
			setReadiness(game, Readiness.ANSWER);
        	        	
        	if (callerIsOnline || (!callerIsOnline && game.hasActivePlayersExceptPlayer(user)))
        	{
        		offlinePlayerChecker.setUpTimeout(game, Readiness.ANSWER);
        	}
        }
		
		return question;
	}

	@Override
	@Transactional
	public void processAnswer(Game game, User player, int variant, int time) {
		
		boolean callerIsOnline = player.getNetStatus() != NetStatus.OFFLINE; 
		if (!callerIsOnline)
		{
			player = userRepository.getUser(player.getId());
			game = gameRepository.reloadGame(game);
			userRepository.lock(player);
			gameRepository.lock(game);
		}
		
		processUserAnswer(game, player, variant, time);
		
		player.setReadiness(Readiness.ANSWER);
        userRepository.updateUser(player);
        updateGame(game);
		
        GameRound round = game.getRound();
        List<User> offlineUsers = game.getOfflinePlayers();
           
        if (callerIsOnline && ( game.allActiveHasReadiness(Readiness.ANSWER)) ||
        		(!callerIsOnline && game.allHasReadiness(Readiness.ANSWER)))
        {   
        	// check if process answer is called by online platers and not from offline checker
        	if (callerIsOnline)
        	{
        		// process offline users
        		for (User offPlayer: offlineUsers)
        		{
        			// wrong answer for offline users
        			processUserAnswer(game, offPlayer, -1, 0);
        		}
        	}
        	
        	// process bots
        	processBotsAnswers(game);
        	
        	int questionNumber = round.getQuestionNumber(player) - 1;
        	Settings settings = settingsService.getSettings();
        	Question question = round.getQuestion(questionNumber, settings.getQuestionNumber());
        	
        	finishQuestionStep(round, question);
        	
        	round.setHumanAnswerCount(0);
        	updateGameRound(round);
        	
        	setReadiness(game, Readiness.RESULT);
        	        	
        	if (callerIsOnline || (!callerIsOnline && game.hasActivePlayersExceptPlayer(player)))
        	{
        		offlinePlayerChecker.setUpTimeout(game, Readiness.RESULT);
        	}
        	        	        	      	        	        	     	
        }		
	}

	@Override
	@Transactional
	public List<QuestionResult> processResult(Game game, User player) throws GameException {
		GameRound round = game.getRound();        
        round.setHumanAnswerCount(round.getHumanAnswerCount() + 1);
    	updateGameRound(round);
    	        
        List<QuestionResult> result = getPersonalResults(game, player);
                
        processResultNow(game, player);      
        
        return result;		
	}
	
	
	@Transactional
	private void processResultNow(Game game, User user) throws GameException
	{
		boolean callerIsOnline = user.getNetStatus() != NetStatus.OFFLINE;
		if (!callerIsOnline)
		{
			user = userRepository.getUser(user.getId());
			game = gameRepository.reloadGame(game);
			userRepository.lock(user);
			gameRepository.lock(game);
		}
		
		GameRound round = game.getRound();
				
		user.setReadiness(Readiness.RESULT);
        userRepository.updateUser(user);
        updateGame(game);
        
        Settings settings = settingsService.getSettings();
        
         // if all online/shadow humans already called /api/game/results after their answers
		if ((callerIsOnline && game.allActiveHasReadiness(Readiness.RESULT)) ||
        		(!callerIsOnline && game.allHasReadiness(Readiness.RESULT)))
        {					
			round.setHumanAnswerCount(0);
	    	updateGameRound(round);
	    	if (round.endOfRound(settings.getQuestionNumber()))
	    	{
	    		boolean hasNextRound = nextRound(game);
	        	if (!hasNextRound)
	        	{       	
	        		setReadiness(game, Readiness.SUMMARY);
	            		            	
	        		saveScores(game);
	        		
	        		offlinePlayerChecker.setUpTimeout(game, Readiness.SUMMARY);
			
	        	} 
	        	else
	        	{
	        		// if next round host is bot or offline user, set category
	        		if (game.getRound().getHost().getType() == UserType.BOT || (game.getRound().getHost().getType() == UserType.HUMAN &&
	        				game.getRound().getHost().getNetStatus() == NetStatus.OFFLINE))
	        		{
	        			setRandomCategory(game);
	        		}
	        		else
	        		{
	        			setReadiness(game, Readiness.CATEGORY);
	        				        			
	        			offlinePlayerChecker.setUpTimeout(game, Readiness.CATEGORY);
	          		}
	        		
	        		setUpAllIntoCategoryReadiness(game);
	        		
	        	}
	    	}
	    	else
	    	{
	    		setReadiness(game, Readiness.QUESTION);
	    		
	    		setUpAllIntoCategoryReadiness(game);
	    		
	    		if (callerIsOnline || (!callerIsOnline && game.hasActivePlayersExceptPlayer(user)))
	    		{
	    			offlinePlayerChecker.setUpTimeout(game, Readiness.QUESTION);
	    		}
			}    	
        }
	}
	
	@Override
	public Game getGameByPlayer(User player) {
		
		return gameOpponentRepository.getGameByPlayer(player);
	}

	@Override
	@Transactional
	public void saveRoundCategory(Game game, Category category) throws GameException {
				
		GameRound gameRound = game.getRound();
		gameRound.setCategory(category);
		
		Settings settings = settingsService.getSettings();
		
		List<Question> questions = questionService.getRandomQuestionList(category, game.getDifficulty(), 
				settings.getQuestionNumber());
		
		for (Question q: questions) {
			gameRound.addQuestion(q);
		}
		
		gameRoundRepository.updateRound(gameRound);
		
		setReadiness(game, Readiness.QUESTION);
				
		if (game.hasActivePlayers())
		{
			offlinePlayerChecker.setUpTimeout(game, Readiness.QUESTION);
		}
		
	}
	
	private void setUpAllIntoCategoryReadiness(Game game)
	{
		List<User> humans = game.getHumans();
		for (User human: humans)
		{
			human.setReadiness(Readiness.CATEGORY);
			userRepository.updateUser(human);
		}
		updateGame(game);
	}
	
	private Question getNextQuestion(Game game, User player) {
		GameRound gameRound = game.getRound();
        int questionNumber = gameRound.getQuestionNumber(player);
        Settings settings = settingsService.getSettings();                
        Question question = gameRound.getQuestion(questionNumber, settings.getQuestionNumber());
        if (question == null)
        {
        	return null;
        }
         
        questionNumber++;
        gameRound.setQuestionNumber(player, questionNumber);
        gameRoundRepository.updateRound(gameRound);
        
        return question;
  	}
	
	@Override
	@Transactional
	public void setRandomCategory(Game game) throws GameException {
		Category category = categoryService.getRandomCategoryFromTheme(game.getTheme());
		if (category == null)
		{
			throw new GameException(0, "There is no category in this theme!");
		}
		
		saveRoundCategory(game, category);
		
	}
	
	private void processUserAnswer(Game game, User player, int variant, int time) {
		GameRound gameRound = game.getRound();
        int questionNumber = gameRound.getQuestionNumber(player);
        questionNumber--; 
        
        Settings settings = settingsService.getSettings();
        Question question = gameRound.getQuestion(questionNumber, settings.getQuestionNumber());
               
        if (player.getType() == UserType.HUMAN)
        {
        	int answerCount = gameRound.getHumanAnswerCount();
        	answerCount++;
        	gameRound.setHumanAnswerCount(answerCount);
        }
       
        gameRoundRepository.updateRound(gameRound);
                
        Answer answer = new Answer(gameRound, question, player, variant, time);
        answerRepository.saveAnswer(answer);
      		
	}

	
	@Override
	public List<QuestionResult> getPersonalResults(Game game, User player) throws GameException {
		List<QuestionResult> results = questionResultRepository.getPersonalResults(game, player);
		return results;
	}

	@Override
	public List<ScoreSummary> getScoreSummary(Game game, User player) {
		player.setReadiness(Readiness.SUMMARY);
        userRepository.updateUser(player);
                
		List<ScoreSummary> summary = scoreSummaryRepository.getSummaryByGame(game);
		
		Collections.sort(summary);
		
		if (game.allActiveHasReadiness(Readiness.SUMMARY))
		{
			finishGame(game);
		}
		
		return summary;
	}
	
	@Override
	public List<ScoreSummary> getScoreSummaryAfterRound(Game game, User player) {
		saveScores(game);
		List<ScoreSummary> summary = scoreSummaryRepository.getSummaryByGame(game);
		
		Collections.sort(summary);
		
		return summary;
	}
	
	@Override
	@Transactional
	public void updateGame(Game game) {
		gameRepository.updateGame(game);		
	}
	
	@Override
	@Transactional
	public void finishGame(Game game) {
				
		userInterestsRepository.updateInterests(game);
		
		game.setGameState(GameState.ENDED);
		updateGame(game);
		
		for (User player: game.getHumans())
		{
			player.setProcessStatus(ProcessStatus.FREE);
			player.setReadiness(Readiness.CATEGORY);
			userRepository.updateUser(player);
		}
		
		gameRepository.cleanUpGame(game);
					
	}
	
	private ArrayList<User> setUpHosts(Game game)
	{
		int playersCount = game.getGameOpponents().size() + 1;
		ArrayList<User> hostOrder = new ArrayList<User>();
		hostOrder.add(game.getCreator());
		for (GameOpponent opponent: game.getGameOpponents())
		{
			hostOrder.add(opponent.getUser());
		}
		
		if (playersCount == 3)
		{
			Random r = new Random();
			hostOrder.add(hostOrder.get(r.nextInt(3)));
		}
		
		if (playersCount == 2)
		{
			hostOrder.add(hostOrder.get(0));
			hostOrder.add(hostOrder.get(1));
		}
		
		Collections.shuffle(hostOrder);
						
		return hostOrder;		
	}

	private void processBotsAnswers(Game game) {
		
    	for (GameOpponent opponent: game.getGameOpponents())
    	{
    		User user = opponent.getUser();
    		if (user.getType() == UserType.BOT)
    		{
    			Question question = getNextQuestion(game, user);
    			 
    			GameBot bot = user.getBot();
    			int botChoice = gameBotService.getBotChoice(bot, question);
    			int botTime = gameBotService.getBotTime(bot, question);
    			
    			processUserAnswer(game, user, botChoice, botTime);
    		}
    	}       		
	}
	
	private GameRound getNextRound(Game game) {
		GameRound previousRound = game.getRound();
		int previousRoundNumber = previousRound.getRoundNumber();
		int nextRoundNumber = previousRoundNumber + 1;
		
		if (nextRoundNumber > 3)
		{
			return null;
		}
		else
		{
			return gameRoundRepository.getRoundByNumber(nextRoundNumber, game);
		}
	}
	
	private boolean nextRound(Game game) {
		GameRound nextRound = getNextRound(game);
		if (nextRound == null)
		{
			return false;
		}
		
		game.setRound(nextRound);
		updateGame(game);
		
		return true;		
	}

	private void finishQuestionStep(GameRound round, Question question) {
		pointsCalculatorService.calculateQuestion(round, question);		
	}

	private void saveScores(Game game)
	{
		List<GameRound> rounds = gameRoundRepository.getAllRoundsByGame(game);
		
		int playersCount = game.getPlayersCount();
		
		int[] scores = new int[playersCount];
		for (GameRound round: rounds)
		{
			scores[0] = scores[0] + round.getPlayer1Points();
			scores[1] = scores[1] + round.getPlayer2Points();
			if (playersCount >= 3)
			{
				scores[2] = scores[2] + round.getPlayer3Points();
			}
			if (playersCount == 4)
			{
				scores[3] = scores[3] + round.getPlayer4Points();
			}
		}
		
		for (int i = 0; i < game.getPlayersCount(); i++)
		{
			int playerNumber = i + 1;
			User player = game.getUserByPlayerNumber(playerNumber);
			
			long wrongAnswers = questionResultRepository.getWrongAnswerCount(game, player);
			long correctAnswers = questionResultRepository.getCorrectAnswerCount(game, player);
			
			ScoreSummary summary = scoreSummaryRepository.getSummaryByGameAndUser(game, player);
			if (summary == null)
			{
				summary = new ScoreSummary(scores[i], player, game, wrongAnswers, correctAnswers);
				scoreSummaryRepository.saveSummary(summary);
			}
			else
			{
				summary.setScore(scores[i]);
				summary.setCorrectAnswers(correctAnswers);
				summary.setWrongAnswers(wrongAnswers);
				scoreSummaryRepository.updateSummary(summary);
			}
		}
	}
	
	private void updateGameRound(GameRound round) {
		gameRoundRepository.updateRound(round);
		
	}

	@Override
	public void setReadiness(Game game, Readiness readiness) {
		gameRepository.setReadiness(game, readiness);
		
	}

	@Override
	@Transactional
	public Readiness getReadiness(Game game) {
		return gameRepository.getReadiness(game);
		
	}

	@Override
	public void cleanUpGame(Game game) {
		
		gameRepository.cleanUpGame(game);
		
	}

	@Override
	public Game getGameById(int gameId) {
		return gameRepository.getGameById(gameId);
		
	}

	

			
}
	

