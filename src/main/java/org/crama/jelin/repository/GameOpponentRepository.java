package org.crama.jelin.repository;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.User;

public interface GameOpponentRepository {
	Game getGameByPlayer(User player);
}
