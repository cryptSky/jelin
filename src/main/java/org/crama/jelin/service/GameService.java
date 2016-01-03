package org.crama.jelin.service;

import org.crama.jelin.model.Game;

public interface GameService {
	boolean startGame(Game game);
	boolean nextRound(Game game);
	boolean finishGame(Game game);
}
