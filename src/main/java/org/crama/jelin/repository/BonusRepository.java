package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Bonus;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserBonus;

public interface BonusRepository {

	List<Bonus> getBonusesByPromocode(String code);

	UserBonus getUserPromoBonus(String code, User user);

	List<Bonus> getBonusesForEarlyRegistration();

	List<Bonus> getBonusesForDaysInGame(int daysInGame);

	List<UserBonus> getUserBonus(Bonus bonus, User user);

	List<UserBonus> getDailyUserBonus(Bonus bonus, User user);

	List<Bonus> getBonusesForSMMInvites(int smmInvites);

	List<Bonus> getBonusesForSMMShares(int smmShares);

	List<Bonus> getBonusesForInitiatedGames(int gamesInitiated);

	List<Bonus> getBonusesForPlayedGames(int gamesPlayed);

}
