package org.crama.jelin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Constants.GameState;
import org.crama.jelin.model.Constants.ProcessStatus;
import org.crama.jelin.model.Constants.Readiness;
import org.crama.jelin.model.Constants.UserType;
import org.crama.jelin.model.Answer;
import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Constants;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameBot;
import org.crama.jelin.model.GameOpponent;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.Question;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.ScoreSummary;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.AnswerRepository;
import org.crama.jelin.repository.GameOpponentRepository;
import org.crama.jelin.repository.GameRepository;
import org.crama.jelin.repository.GameRoundRepository;
import org.crama.jelin.repository.QuestionResultRepository;
import org.crama.jelin.repository.ScoreSummaryRepository;
import org.crama.jelin.repository.UserRepository;
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
	
	@Override
	@Transactional
	public void startGame(Game game) throws GameException {
		List<User> hosts = setUpHosts(game);
						
		List<GameRound> gameRounds = new ArrayList<GameRound>();

		for (int round = 0; round < 4; round++)
		{
			GameRound gameRound = new GameRound(game, round, hosts.get(round));
			gameRounds.add(gameRound);
		}
		
		gameRoundRepository.saveOrUpdateRounds(gameRounds);
		
		game.setRound(gameRounds.get(0));
		game.setGameState(GameState.IN_PROGRESS);
		game.setReadiness(Readiness.CATEGORY);
		
		gameRepository.updateGame(game);
		
		
		if (hosts.get(0).getType() == UserType.BOT)
		{
			setRandomCategory(game);
			
		}
		
		gameRoundRepository.saveOrUpdateRounds(gameRounds);
		gameRepository.updateGame(game);
		
		User creator = game.getCreator();
		creator.setProcessStatus(ProcessStatus.INGAME);
		userRepository.updateUser(creator);
		
		for (GameOpponent opponent: game.getGameOpponents())
		{
			User player = opponent.getUser();
			player.setProcessStatus(ProcessStatus.INGAME);
			userRepository.updateUser(player);
		}
		
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

	@Override
	public Game getGameByPlayer(User player) {
		
		return gameOpponentRepository.getGameByPlayer(player);
	}

	@Override
	public void saveRoundCategory(Game game, Category category) throws GameException {
		GameRound gameRound = game.getRound();
		gameRound.setCategory(category);
		for (int i = 0; i < Constants.questionsNumber; i++)
		{
			Question question = questionService.getRandomQuestion(category, game.getDifficulty());
			gameRound.addQuestion(question);
		}
		gameRoundRepository.updateRound(gameRound);
		game.setReadiness(Readiness.QUESTION);
		updateGame(game);
		
	}

	@Override
	public void processAnswer(Game game, User player, int variant, int time) {
		GameRound gameRound = game.getRound();
        int index = gameRound.getQuestionNumber(player);
        Question question = gameRound.getQuestion(index);
        int playerNumber = game.getPlayerNumberByUser(player);
        
        if (player.getType() == UserType.HUMAN)
        {
        	int answerCount = gameRound.getHumanAnswerCount();
        	answerCount++;
        	gameRound.setHumanAnswerCount(answerCount);
        }
        
        gameRoundRepository.updateRound(gameRound);
        
        Answer answer = new Answer(gameRound, question, playerNumber, variant, time);
        answerRepository.saveAnswer(answer);
      		
	}

	@Override
	public Question getNextQuestion(Game game, User player) {
		GameRound gameRound = game.getRound();
        int questionNumber = gameRound.getQuestionNumber(player);
        
        if (questionNumber >= Constants.questionsNumber)
        {
        	return null;
        }
        
        Question question = gameRound.getQuestion(questionNumber);
        questionNumber++;
        
        gameRound.setQuestionNumber(player, questionNumber);
        gameRoundRepository.updateRound(gameRound);
  
        return question;
        
	}

	@Override
	public void processBotsAnswers(Game game) {
		
    	for (GameOpponent opponent: game.getGameOpponents())
    	{
    		User user = opponent.getUser();
    		if (user.getType() == UserType.BOT)
    		{
    			Question question = getNextQuestion(game, user);
    			 
    			GameBot bot = user.getBot();
    			int botChoice = gameBotService.getBotChoice(bot, question);
    			int botTime = gameBotService.getBotTime(bot, question);
    			
    			processAnswer(game, user, botChoice, botTime);
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
			return gameRoundRepository.getRoundByNumber(nextRoundNumber);
		}
	}
	
	@Override
	public boolean nextRound(Game game) {
		GameRound nextRound = getNextRound(game);
		if (nextRound == null)
		{
			return false;
		}
		
		game.setRound(nextRound);
		updateGame(game);
		
		return true;		
	}

	@Override
	public void finishRound(GameRound round) {
		pointsCalculatorService.calculate(round);
		
	}

	@Override
	public List<QuestionResult> getPersonalRoundResults(Game game, User player) throws GameException {
		return questionResultRepository.getPersonalRoundResults(game, player);
	}

	@Override
	public void finishGame(Game game) {
		saveScores(game);		
				
		// game.setGameState(GameState.ENDED);
		// updateGame(game);
		
	}

	private void saveScores(Game game)
	{
		List<GameRound> rounds = gameRoundRepository.getAllRoundsByGame(game);
		
		int[] scores = new int[game.getPlayersCount()];
		for (GameRound round: rounds)
		{
			scores[0] = scores[0] + round.getPlayer1Points();
			scores[1] = scores[1] + round.getPlayer2Points();
			scores[2] = scores[2] + round.getPlayer3Points();
			scores[3] = scores[3] + round.getPlayer4Points();
		}
		
		for (int i = 0; i < game.getPlayersCount(); i++)
		{
			int playerNumber = i + 1;
			User player = game.getUserByPlayerNumber(playerNumber);
			ScoreSummary summary = new ScoreSummary(scores[i], player, game);
			
			scoreSummaryRepository.saveSummary(summary);
		}
	}
	
	@Override
	public List<ScoreSummary> getScoreSummary(Game game) {
		List<ScoreSummary> summary = scoreSummaryRepository.getSummaryByGame(game);
		
		return summary;
	}

	@Override
	public void updateGame(Game game) {
		gameRepository.updateGame(game);
		
	}
	
	@Override
	public void updateGameRound(GameRound round) {
		gameRoundRepository.updateRound(round);
		
	}

	@Override
	public void setRandomCategory(Game game) throws GameException {
		Category category = categoryService.getRandomCategoryFromTheme(game.getTheme());
		if (category == null)
		{
			throw new GameException(0, "There is no category in this theme!");
		}
		
		saveRoundCategory(game, category);
		
	}

		
}
	

