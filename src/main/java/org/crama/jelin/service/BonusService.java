package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.Bonus;
import org.crama.jelin.model.Constants.PromocodeStatus;
import org.crama.jelin.model.User;

public interface BonusService {

	List<Bonus> getBonusesByPromocode(String code, User user);

	PromocodeStatus checkPromocode(String code, User user);

	List<Bonus> getAppEnterBonuses(User user);

	List<Bonus> getEarlyRegisterBonuses(User user);

	List<Bonus> getSMMInvitesBonuses(User user);

	List<Bonus> getSMMSharesBonuses(User user);

	List<Bonus> getGameInitiatedBonuses(User user);

	List<Bonus> getGamePlayedBonuses(User user);

}
