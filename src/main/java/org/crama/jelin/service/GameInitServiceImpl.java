package org.crama.jelin.service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

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
	
	//TODO get it out from here and change to 8 and 2
	public static final int TIMEOUT = 20;
	public static final int CHECK_TIMEOUT = 5;
	
	
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
	public String inviteUser(Game game, User creator, User opponent) {
		ProcessStatus status = userRepository.getProcessStatus(ProcessStatus.INVITING);
		opponent.setProcessStatus(status);
		userRepository.updateUser(opponent);
		
		Set<GameOpponent> opponents = game.getGameOpponents();
		InviteStatus inviteStatus = gameInitRepository.getInviteStatus(InviteStatus.OPEN);
		System.out.println(inviteStatus.getStatus());
		GameOpponent newOpponent = new GameOpponent(opponent, game, inviteStatus);
		
		boolean isNewOpponent = true;
		for (GameOpponent o: opponents) {
			 
			 if (o.getUser().equals(opponent)) {
				 
				 System.out.println("User was already invited to the game. Update invitatioon with OPEN status");
				 o.setInviteStatus(inviteStatus);
				 isNewOpponent = false;
				 break;
			 }
		}
		if (isNewOpponent) {
			 opponents.add(newOpponent);
			game.setGameOpponents(opponents);
		}
		gameInitRepository.updateGame(game);
		
		Game updatedGame = null;
		
		boolean invitationHandled = false;
		int numOfChecks = TIMEOUT / CHECK_TIMEOUT;
		for (int i = 0; i < numOfChecks; i++) {
			// check status every CHECK_TIMEOUT seconds
			try {
				//1000 milliseconds is one second
			    Thread.sleep(1000 * CHECK_TIMEOUT);                 
			} catch(InterruptedException ex) {
				System.out.println("Interrupted Exception");
			    Thread.currentThread().interrupt();
			}
			
			System.out.println("Check invitation: " + i);
			
			gameInitRepository.clearSession();
			
			updatedGame = getCreatedGame(creator);
			
			if (!checkInviteStatus(updatedGame)) {
				System.out.println("Invitation handled. Return from the method");
				invitationHandled = true;
				
				GameOpponent go = gameInitRepository.getGameOpponent(updatedGame, opponent);
				return go.getInviteStatus().getStatus(); 
				
			}
		}
		
		
		
		//after TIMEOUT
		if (!invitationHandled) {
			System.out.println("Invitation expired!");
			InviteStatus statusExpired = gameInitRepository.getInviteStatus(InviteStatus.EXPIRED);
			 System.out.println("Invite Status: " + statusExpired);
			 //Set<GameOpponent> opponents = game.getGameOpponents();
			 //System.out.println("Opponents: " + opponents);
			 
			 gameInitRepository.clearSession();
			 updatedGame = getCreatedGame(creator);
			 
			 
			 for (GameOpponent o: updatedGame.getGameOpponents()) {
				 System.out.println("inside a loop: " + o);
				 if (o.getUser().equals(opponent)) {
					 
					 System.out.println("Update status: " + statusExpired);
					 o.setInviteStatus(statusExpired);
					 
					 gameInitRepository.updateGame(updatedGame);
					 
					 User op = o.getUser();
					 
					 //change opponent status to free
					 ProcessStatus freeStatus = userRepository.getProcessStatus(ProcessStatus.FREE);
					 op.setProcessStatus(freeStatus);
					 userRepository.updateUser(op);
					 
					 return statusExpired.getStatus();
					 
				 }
			}
		}
		
		return null;
		
		//TODO fix it!
		/*Runnable inviteExpireProcess = new Runnable() {
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
					
					System.out.println("Update user process status");
					ProcessStatus status = userRepository.getProcessStatus(ProcessStatus.CALLING);
					creator.setProcessStatus(status);
					userRepository.updateUser(creator);
				}
				 catch (Exception e) {
					 System.out.println(e.getMessage());
					 e.printStackTrace();
				 }
			 }
	    };
	    this.inviteTimer = this.scheduler.schedule(inviteExpireProcess, TIMEOUT, TimeUnit.SECONDS);*/
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
			 
			 System.out.println("Confirm invite: " + o.getUser().getUsername() + ", " + o.getInviteStatus().getStatus());
			 
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
				System.out.println("OPEN invitation: " + o.getUser().getUsername() + ", " + o.getInviteStatus().getStatus());
				return true;
			}
		}
		
		return false;
	}

}
