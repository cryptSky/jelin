package org.crama.jelin.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Constants.GameState;
import org.crama.jelin.model.Constants.InviteStatus;
import org.crama.jelin.model.Constants.ProcessStatus;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameOpponent;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.GameInitRepository;
import org.crama.jelin.repository.GameOpponentRepository;
import org.crama.jelin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gameInitService")
public class GameInitServiceImpl implements GameInitService {

	@Autowired
	private GameInitRepository gameInitRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private GameOpponentRepository gameOpponentRepository;
	@Autowired
	private UserService userService;
	
	//private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	//private ScheduledFuture<?> inviteTimer;
	
	//TODO get it out from here and change to 8 and 2
	public static final int TIMEOUT = 20;
	public static final int CHECK_TIMEOUT = 5;
	
	
	@Override
	public boolean initGame(User creator, Category theme, boolean random) {
		
		Game game = new Game(theme, random);
		game.setGameState(GameState.CREATED);
		
		//Set<GameUser> gameUserSet = new HashSet<GameUser>();
		//GameUser gameUser = new GameUser(creator, game, true);
		//gameUserSet.add(gameUser);
		//game.setGamePlayerSet(gameUserSet);
		
		game.setCreator(creator);
		game.setInitDate(new Date());
		
		return gameInitRepository.saveGame(game);
	}
	
	@Override
	public void initGame(User creator, Difficulty difficulty) {
		Game game = new Game(difficulty);
		game.setGameState(GameState.CREATED);
		game.setCreator(creator);
		game.setInitDate(new Date());
		
		gameInitRepository.saveGame(game);
	}


	@Override
	public boolean updateDifficulty(Game game, Difficulty difficulty) {
		game.setDifficulty(difficulty);
		return gameInitRepository.updateGame(game);
	}

	@Override
	public void updateCategory(Game game, Category category, boolean random) {
		game.setTheme(category);
		game.setRandom(random);
		gameInitRepository.updateGame(game);
	}
	
	@Override
	public Game getCreatedGame(User creator) {
		Game game = gameInitRepository.getCreatedGame(creator);
		return game;
	}

	@Override
	public void removeOpponent(Game game, int userId) throws GameException {
		System.out.println("inside remove opponent method:");
		// update user status to free
		User user = userRepository.getUser(userId);
		userService.checkUserAuthorized(user);
		user.setProcessStatus(ProcessStatus.FREE);
		userRepository.updateUser(user);
		
		gameInitRepository.removeGameOpponent(game, user);
		
		
		/*Set<GameOpponent> opponents = game.getGameOpponents();
		for (GameOpponent o:new HashSet<GameOpponent>(opponents)) {
			System.out.println(o);
			if (o.getUser().getId() == userId) {
				System.out.println("remove opponent: " + userId);
				opponents.remove(o);
				
			}
		}
		System.out.println("Opponents after removal:");
		for (GameOpponent o: opponents) {
			System.out.println(o);
		}
		game.setGameOpponents(opponents);
		gameInitRepository.updateGame(game);*/
	}

	@Override
	public Set<User> getGameOpponents(Game game) {
		Set<GameOpponent> opponents = game.getGameOpponents();
		Set<User> acceptedOpponents = new HashSet<User>();
		System.out.println("Op: " + opponents + ", " + opponents.size());
		for (GameOpponent o: opponents) {
			System.out.println("Op: " + o);
			if (o.getInviteStatus().equals(InviteStatus.ACCEPTED)) {
				System.out.println("Accepted opponent: " + o);
				acceptedOpponents.add(o.getUser());
			}
		}
		return acceptedOpponents;
	}

	@Override
	public InviteStatus inviteUser(Game game, User creator, User opponent) {
		opponent.setProcessStatus(ProcessStatus.INVITING);
		userRepository.updateUser(opponent);
		
		Set<GameOpponent> opponents = game.getGameOpponents();
		InviteStatus inviteStatus = InviteStatus.OPEN;
		System.out.println(inviteStatus);
		GameOpponent newOpponent = new GameOpponent(opponent, game, inviteStatus);
		
		boolean isNewOpponent = true;
		for (GameOpponent o: opponents) {
			
			System.out.println("Game opponent: " + o);
			System.out.println(o.getUser().getUsername() + ", " + o.getUser().getId() + ", " + 
					opponent.getUsername() + ", " + opponent.getId());
			
			//if (o.getUser().equals(opponent)) {
			 if (o.getUser().getId() == opponent.getId()) {
				 
				 System.out.println("User was already invited to the game. Update invitatioon with OPEN status");
				 o.setInviteStatus(inviteStatus);
				 isNewOpponent = false;
				 break;
			 }
		}
		System.out.println("Is new opponent: " + isNewOpponent);
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
				return go.getInviteStatus(); 
				
			}
		}
		
		
		
		//after TIMEOUT
		if (!invitationHandled) {
			System.out.println("Invitation expired!");
			InviteStatus statusExpired = InviteStatus.EXPIRED;
			 System.out.println("Invite Status: " + statusExpired);
			 //Set<GameOpponent> opponents = game.getGameOpponents();
			 //System.out.println("Opponents: " + opponents);
			 
			 gameInitRepository.clearSession();
			 updatedGame = getCreatedGame(creator);
			 
			 
			 for (GameOpponent o: updatedGame.getGameOpponents()) {
				 System.out.println("inside a loop: " + o);
				 if (o.getUser().getId() == opponent.getId()) {
					 
					 System.out.println("Update status: " + statusExpired);
					 o.setInviteStatus(statusExpired);
					 
					 gameInitRepository.updateGame(updatedGame);
					 
					 User op = o.getUser();
					 
					 op.setProcessStatus(ProcessStatus.FREE);
					 userRepository.updateUser(op);
					 
					 return statusExpired;
					 
				 }
			}
		}
		
		return null;
		
	}

	@Override
	public Game getInviteGame(User user) {
		
		return gameInitRepository.getInviteGame(user);
	}

	@Override
	public void confirmInvite(Game game, User user) {
	
		boolean is2Free = true;
		boolean is3Free = true;
		boolean is4Free = true;
		
		InviteStatus statusAccepted = InviteStatus.ACCEPTED;
		Set<GameOpponent> opponents = game.getGameOpponents();
		
		//check free player numbers
		for (GameOpponent o: opponents) {
			if (o.getInviteStatus().equals(InviteStatus.ACCEPTED)) {
				int playerNum = o.getPlayerNum();
				if (playerNum == 2) {
					is2Free = false;
				}
				else if (playerNum == 3) {
					is3Free = false;
				}
				else if (playerNum == 4) {
					is4Free = false;
				}
			}
		}
		
		 for (GameOpponent o: opponents) {
			 
			 System.out.println("Confirm invite: " + o.getUser().getUsername() + ", " + o.getInviteStatus());
			 
			 if (o.getUser().getId() == user.getId()) {
			 //if (o.getUser().equals(user)) {
				 
				 //accept invitation
				 o.setInviteStatus(statusAccepted);
				 if (is2Free) {
					 o.setPlayerNum(2);
				 }
				 else if (is3Free) {
					 o.setPlayerNum(3);
				 }
				 else if (is4Free) {
					 o.setPlayerNum(4);
				 }
				 gameInitRepository.updateGame(game);
				 
				 user.setProcessStatus(ProcessStatus.WAITING);
				 userRepository.updateUser(user);
				 
				 break;
				 
			 }
		}
	}

	@Override
	public void refuseInvite(Game game, User user) {
		InviteStatus statusRejected = InviteStatus.REJECTED;
		Set<GameOpponent> opponents = game.getGameOpponents();
		 
		 for (GameOpponent o: opponents) {
			 if (o.getUser().equals(user)) {
				 
				 //reject invitation
				 o.setInviteStatus(statusRejected);
				 gameInitRepository.updateGame(game);
				 
				 user.setProcessStatus(ProcessStatus.FREE);
				 userRepository.updateUser(user);
				 
				 break;
				 
			 }
		}
	}

	@Override
	public boolean checkInviteStatus(Game game) {
		Set<GameOpponent> opponents = game.getGameOpponents();
		for (GameOpponent o: opponents) {
			if (o.getInviteStatus().equals(InviteStatus.OPEN)) {
				//there is open invite status
				System.out.println("OPEN invitation: " + o.getUser().getUsername() + ", " + o.getInviteStatus());
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void checkGameCreated(Game game) throws GameException {
		if (game == null) {
			throw new GameException(401, "Game is not created");
		}
	}

	@Override
	public void checkGameRandom(Game game) throws GameException {
		if (game.getRandom()) {
			throw new GameException(402, "Game is random");
		}
	}

	@Override
	public void checkNumOfOpponents(Game game) throws GameException {
		if (getGameOpponents(game).size() >=3) {
			throw new GameException(701, "There are already 3 opponents that have accepted game offer. Can't invite more opponents");
		}
	}

	@Override
	public void closeGame(Game game) {
		game.setGameState(GameState.ENDED);
		game.getCreator().setProcessStatus(ProcessStatus.FREE);
		
		gameInitRepository.updateGame(game);
		Set<GameOpponent> opponents = game.getGameOpponents();
		for (GameOpponent go: opponents) {
			User o = go.getUser();
			o.setProcessStatus(ProcessStatus.FREE);
			userRepository.updateUser(o);
		}
	}

	@Override
	public void addGameOpponent(Game game, User user) {
		GameOpponent opponent = new GameOpponent(user, game, InviteStatus.ACCEPTED);
		game.addGameOpponent(opponent);
		gameInitRepository.updateGame(game);
		
	}

}
