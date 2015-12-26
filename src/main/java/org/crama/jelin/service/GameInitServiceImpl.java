package org.crama.jelin.service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameOpponent;
import org.crama.jelin.model.GameState;
import org.crama.jelin.model.InviteStatus;
import org.crama.jelin.model.ProcessStatus;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.GameInitRepository;
import org.crama.jelin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gameInitService")
public class GameInitServiceImpl implements GameInitService {

	@Autowired
	private GameInitRepository gameInitRepository;
	@Autowired
	private UserRepository userRepository;
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> inviteTimer;
	
	//TODO get it out from here
	public static final int TIMEOUT = 8;
	
	@Override
	public boolean initGame(User creator, Category theme, boolean random) {
		
		Game game = new Game(theme, random);
		GameState state = gameInitRepository.getGameState(GameState.CREATED);
		game.setGameState(state);
		//Set<GameUser> gameUserSet = new HashSet<GameUser>();
		//GameUser gameUser = new GameUser(creator, game, true);
		//gameUserSet.add(gameUser);
		//game.setGamePlayerSet(gameUserSet);
		
		game.setCreator(creator);
		
		return gameInitRepository.saveGame(game);
	}

	@Override
	public boolean updateDifficulty(Game game, Difficulty difficulty) {
		game.setDifficulty(difficulty);
		return gameInitRepository.updateGame(game);
	}

	@Override
	public Game getCreatedGame(User creator) {
		Game game = gameInitRepository.getCreatedGame(creator);
		return game;
	}

	@Override
	public void removeOpponent(Game game, int userId) {
		// update user status to free
		User user = userRepository.getUser(userId);
		ProcessStatus ps = userRepository.getProcessStatus(ProcessStatus.FREE);
		user.setProcessStatus(ps);
		userRepository.updateUser(user);
		
		Set<GameOpponent> opponents = game.getGameOpponents();
		for (GameOpponent o:new HashSet<GameOpponent>(opponents)) {
			if (o.getUser().getId() == userId) {
				opponents.remove(o);
				
			}
		}
		gameInitRepository.updateGame(game);
	}

	@Override
	public Set<User> getGameOpponents(Game game) {
		Set<GameOpponent> opponents = game.getGameOpponents();
		Set<User> acceptedOpponents = new HashSet<User>();
		for (GameOpponent o: opponents) {
			if (o.getInviteStatus().getStatus().equals(InviteStatus.ACCEPTED)) {
				acceptedOpponents.add(o.getUser());
			}
		}
		return acceptedOpponents;
	}

	@Override
	public void inviteUser(Game game, User creator, User opponent) {
		ProcessStatus status = userRepository.getProcessStatus(ProcessStatus.INVITING);
		opponent.setProcessStatus(status);
		userRepository.updateUser(opponent);
		
		Set<GameOpponent> opponents = game.getGameOpponents();
		InviteStatus inviteStatus = gameInitRepository.getInviteStatus(InviteStatus.OPEN);
		System.out.println(inviteStatus.getStatus());
		GameOpponent newOpponent = new GameOpponent(opponent, game, inviteStatus);
		opponents.add(newOpponent);
		game.setGameOpponents(opponents);
		gameInitRepository.updateGame(game);
		
		//TODO fix it!
		Runnable inviteExpireProcess = new Runnable() {
			 public void run() {
				 System.out.println("Invitation expired!");
				 
				 try {
					 InviteStatus statusExpired = gameInitRepository.getInviteStatus(InviteStatus.EXPIRED);
					 System.out.println("Invite Status: " + statusExpired);
					 Set<GameOpponent> opponents = game.getGameOpponents();
					 System.out.println("Opponents: " + opponents);
					 for (GameOpponent o: opponents) {
						 System.out.println("inside a loop: " + o);
						 if (o.getUser().equals(opponent)) {
							 
							 System.out.println("Update status: " + statusExpired);
							 o.setInviteStatus(statusExpired);
							 gameInitRepository.updateGame(game);
							 break;
						 }
					}
					
					/*System.out.println("Update user process status");
					ProcessStatus status = userRepository.getProcessStatus(ProcessStatus.CALLING);
					creator.setProcessStatus(status);
					userRepository.updateUser(creator);*/
				}
				 catch (Exception e) {
					 System.out.println(e.getMessage());
					 e.printStackTrace();
				 }
			 }
	    };
	    this.inviteTimer = this.scheduler.schedule(inviteExpireProcess, TIMEOUT, TimeUnit.SECONDS);
	}

	@Override
	public Game getInviteGame(User user) {
		
		return gameInitRepository.getInviteGame(user);
	}

	@Override
	public void confirmInvite(Game game, User user) {
	
		InviteStatus statusAccepted = gameInitRepository.getInviteStatus(InviteStatus.ACCEPTED);
		Set<GameOpponent> opponents = game.getGameOpponents();
		 
		 for (GameOpponent o: opponents) {
			 if (o.getUser().equals(user)) {
				 
				 //accept invitation
				 o.setInviteStatus(statusAccepted);
				 gameInitRepository.updateGame(game);
				 
				 //set user to WAITING status
				 ProcessStatus ps = userRepository.getProcessStatus(ProcessStatus.WAITING);
				 user.setProcessStatus(ps);
				 userRepository.updateUser(user);
				 
				 break;
				 
			 }
		}
	}

	@Override
	public void refuseInvite(Game game, User user) {
		InviteStatus statusRejected = gameInitRepository.getInviteStatus(InviteStatus.REJECTED);
		Set<GameOpponent> opponents = game.getGameOpponents();
		 
		 for (GameOpponent o: opponents) {
			 if (o.getUser().equals(user)) {
				 
				 //reject invitation
				 o.setInviteStatus(statusRejected);
				 gameInitRepository.updateGame(game);
				 
				 //set user to FREE status
				 ProcessStatus ps = userRepository.getProcessStatus(ProcessStatus.FREE);
				 user.setProcessStatus(ps);
				 userRepository.updateUser(user);
				 
				 break;
				 
			 }
		}
	}

	@Override
	public boolean checkInviteStatus(Game game) {
		Set<GameOpponent> opponents = game.getGameOpponents();
		for (GameOpponent o: opponents) {
			if (o.getInviteStatus().getStatus().equals(InviteStatus.OPEN)) {
				//there is open invite status
				return true;
			}
		}
		return false;
	}

}
