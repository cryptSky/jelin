package org.crama.jelin.util;

import java.util.ArrayList;
import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Constants.NetStatus;
import org.crama.jelin.model.Constants.Readiness;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.Question;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.AnswerRepository;
import org.crama.jelin.repository.GameRepository;
import org.crama.jelin.repository.QuestionResultRepository;
import org.crama.jelin.repository.UserRepository;
import org.crama.jelin.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class OfflinePlayerWorker implements Runnable {

	private Game game;
	private Readiness condition;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionResultRepository questionResultRepository;
			
	public OfflinePlayerWorker(Game game, Readiness condition) {
		super();
		this.game = game;
		this.condition = condition;
	}
	
	@Override
	public void run() {
		if (game.getReadiness() != condition)
		{
			List<User> offlineHumans = getOfflineUsers();
			for (User human : offlineHumans)
			{
				human.setNetStatus(NetStatus.OFFLINE);
				userRepository.updateNetStatus(human, NetStatus.OFFLINE);
			}
			
			gameService.updateGame(game);
		}

	}
	
	private List<User> getOfflineUsers()
	{
		List<User> users = game.getHumans();
		List<User> offlineUsers = new ArrayList<User>();
		GameRound round = game.getRound();
		
		switch(condition)
		{
			case CATEGORY:	offlineUsers.add(game.getRound().getHost()); 
						    break;
			case QUESTION:	for (User user: users)
							{
								if (!round.alreadyGotQuestion(user))
								{
									offlineUsers.add(user);
								}
							}
							break;
			case ANSWER:	Question question = round.currentQuestion();	 
							List<User> online = answerRepository.getUsersAnswered(round, question);
							for (User player: users)
							{
								if (!online.contains(player))
								{
									offlineUsers.add(player);
								}
							}
							break;
			case RESULT:	Question quest = round.currentQuestion();
							List<User> offline = questionResultRepository.getPlayersWithoutResult(round, quest);
							offlineUsers.addAll(offline);
							break;
			default: break;
		}
		
		return offlineUsers;
	}
	
	private void initialOfflineUpdate(Readiness condition, List<User> offlineUsers) throws GameException
	{
		GameRound round = game.getRound();
		switch(condition)
		{
			case CATEGORY:	gameService.setRandomCategory(game);
							break;
			case QUESTION:	for (User player: offlineUsers)
							{
								int qnumber = round.getQuestionNumber(player) + 1;
								round.setQuestionNumber(player, qnumber);
							}
							
							game.setReadiness(Readiness.ANSWER);
							gameService.updateGame(game);
							break;
			case ANSWER:	round.setHumanAnswerCount(round.getHumanAnswerCount() + offlineUsers.size());
							gameService.updateGameRound(round);
							
							for (User player: offlineUsers)
							{
								gameService.processAnswer(game, player, -1, 0);
							}
							
							game.setReadiness(Readiness.RESULT);
							gameService.updateGame(game);
							break;
			case RESULT:	round.setHumanAnswerCount(round.getHumanAnswerCount() + offlineUsers.size());
							gameService.updateGameRound(round);	
				
							game.setReadiness(Readiness.QUESTION);
							gameService.updateGame(game);
							break;
			default: break;
		}
	}
	
}
