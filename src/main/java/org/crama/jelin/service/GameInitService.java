package org.crama.jelin.service;

import java.util.Set;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Constants.GameState;
import org.crama.jelin.model.Constants.InviteStatus;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.User;
import org.crama.jelin.model.json.UserJson;

public interface GameInitService {
	boolean initGame(User creator, Category theme, boolean random);
	
	void initGame(User creator, Difficulty difficulty);
	
	boolean updateDifficulty(Game game, Difficulty difficulty);

	Game getCreatedGame(User creator);
	
	void removeOpponent(Game game, int userId) throws GameException;

	Set<UserJson> getGameOpponents(Game game);
	
	void addGameOpponent(Game game, User opponent);

	InviteStatus inviteUser(Game game, User creator, User opponent);

	Game getInviteGame(User user);

	void confirmInvite(Game game, User user);

	void refuseInvite(Game game, User user);

	boolean checkInviteStatus(Game game);

	void checkGameCreated(Game game) throws GameException;

	void checkGameRandom(Game game) throws GameException;

	void updateCategory(Game game, Category category, boolean random);

	void checkNumOfOpponents(Game game) throws GameException;

	void closeGame(Game game);

	Game getGame(User creator, GameState state);
	
	
}
