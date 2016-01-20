package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameRound;

public interface GameRoundRepository {
	void saveOrUpdateRounds(List<GameRound> GameRound);
	void updateRound(GameRound round);
	
	GameRound getRoundByNumber(int roundNumber, Game game);
	List<GameRound> getAllRoundsByGame(Game game);
}
