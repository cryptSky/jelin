package org.crama.jelin.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.crama.jelin.model.Bonus;
import org.crama.jelin.model.Character;
import org.crama.jelin.model.Constants;
import org.crama.jelin.model.Constants.PromocodeStatus;
import org.crama.jelin.model.Constants.Status;
import org.crama.jelin.model.Enhancer;
import org.crama.jelin.model.Group;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserBonus;
import org.crama.jelin.model.UserEnhancer;
import org.crama.jelin.model.UserStatistics;
import org.crama.jelin.repository.BonusRepository;
import org.crama.jelin.repository.UserRepository;
import org.crama.jelin.service.BonusService;
import org.crama.jelin.util.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bonusService")
public class BonusServiceImpl implements BonusService {

	@Autowired
	private BonusRepository bonusRepository; 
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public PromocodeStatus checkPromocode(String code, User user) {
		
		List<Bonus> bonuses = bonusRepository.getBonusesByPromocode(code);
		if (bonuses.size() == 0) {
			return PromocodeStatus.NOT_FOUND;
		}
		
		UserBonus userBonus = bonusRepository.getUserPromoBonus(code, user);
		if (userBonus != null) {
			return PromocodeStatus.ALREADY_USED;
		}
		
		return PromocodeStatus.AVAILABLE;
	}
	
	@Override
	public List<Bonus> getBonusesByPromocode(String code, User user) {
		if (!checkPromocode(code, user).equals(PromocodeStatus.AVAILABLE)) {
			System.out.println("Promocode is not available");
			return null;
		}
		List<Bonus> bonuses = bonusRepository.getBonusesByPromocode(code);
		
		for (Bonus bonus: bonuses) {
			useBonus(bonus, user);
		}
		return bonuses;
	}

	

	@Override
	public List<Bonus> getEarlyRegisterBonuses(User user) {
		//check if user registered within early register interval
		Date registerDate = new Date(user.getRegisterDate().getTime());
		System.out.println(registerDate + ", " + registerDate.getClass());
		LocalDate localRegisterDate = DateConverter.toLocalDate(registerDate);
		
		//TODO change it for server config attributes
		LocalDate bonusStartDate = Constants.EARLY_SIGNUP_START_DATE;
		LocalDate bonusEndDate = Constants.EARLY_SIGNUP_END_DATE;
		
		if (localRegisterDate.isAfter(bonusStartDate) && localRegisterDate.isBefore(bonusEndDate)) {
			
			List<Bonus> bonuses = bonusRepository.getBonusesForEarlyRegistration();
			
			List<UserBonus> userBonuses = user.getBonusList();
			
			//check if user already have these bonuses
			bonuses: for (Bonus bonus: new ArrayList<Bonus>(bonuses)) {
				for (UserBonus userBonus: userBonuses) {
					if (userBonus.getBonus().equals(bonus)) {
						bonuses.remove(bonus);
						continue bonuses;
					}
				}
			}
			
			for (Bonus bonus: bonuses) {
				useBonus(bonus, user);
			}
			
			return bonuses;
		}
		
		return null;
	}
	
	@Override
	public List<Bonus> getAppEnterBonuses(User user) {
		//get how many days user played each day
		int daysInGame = user.getUserStatistics().getDaysInGame();
		List<Bonus> bonuses = bonusRepository.getBonusesForDaysInGame(daysInGame); 
		
		List<Bonus> validBonuses = checkBonuses(bonuses, user);
		
		for (Bonus bonus: validBonuses) {
			useBonus(bonus, user);
		}
		
		return validBonuses;
	}

	@Override
	public List<Bonus> getSMMInvitesBonuses(User user) {
		
		int smmInvites = user.getUserStatistics().getSmmInvites();
		List<Bonus> bonuses = bonusRepository.getBonusesForSMMInvites(smmInvites); 
		
		List<Bonus> validBonuses = checkBonuses(bonuses, user);
		
		for (Bonus bonus: validBonuses) {
			useBonus(bonus, user);
		}
		
		return validBonuses;
	}

	@Override
	public List<Bonus> getSMMSharesBonuses(User user) {
		
		int smmShares = user.getUserStatistics().getSmmShares();
		List<Bonus> bonuses = bonusRepository.getBonusesForSMMShares(smmShares); 
		
		List<Bonus> validBonuses = checkBonuses(bonuses, user);
		
		for (Bonus bonus: validBonuses) {
			useBonus(bonus, user);
		}
		
		return validBonuses;
	}
	
	@Override
	public List<Bonus> getGameInitiatedBonuses(User user) {
		int gamesInitiated = user.getUserStatistics().getGamesInitiated();
		List<Bonus> bonuses = bonusRepository.getBonusesForInitiatedGames(gamesInitiated); 
		
		List<Bonus> validBonuses = checkBonuses(bonuses, user);
		
		for (Bonus bonus: validBonuses) {
			useBonus(bonus, user);
		}
		
		return validBonuses;
	}

	@Override
	public List<Bonus> getGamePlayedBonuses(User user) {
		int gamesPlayed = user.getUserStatistics().getGamesPlayed();
		List<Bonus> bonuses = bonusRepository.getBonusesForPlayedGames(gamesPlayed); 
		
		List<Bonus> validBonuses = checkBonuses(bonuses, user);
		
		for (Bonus bonus: validBonuses) {
			useBonus(bonus, user);
		}
		
		return validBonuses;
	}
	
	
	//PRIVATE METHODS
	
	private List<Bonus> checkBonuses(List<Bonus> bonuses, User user) {
		
		for (Bonus bonus: new ArrayList<Bonus>(bonuses)) {
			
			int limit = bonus.getLimit();
			int dailyLimit = bonus.getDailyLimit();
			
			List<UserBonus> userBonuses = bonusRepository.getUserBonus(bonus, user);
			int numOfBonuses = userBonuses.size();
			List<UserBonus> dailyUserBonuses = bonusRepository.getDailyUserBonus(bonus, user);
			int numOfDailyBonuses = dailyUserBonuses.size();
			
			if (limit != 0 && numOfBonuses == limit) {
				System.out.println("You have reached limit for this bonus: " + limit);
				bonuses.remove(bonus);
				continue;
			}
			if (dailyLimit != 0 && numOfDailyBonuses == dailyLimit) {
				System.out.println("You have reached daily limit for this bonus: " + dailyLimit);
				bonuses.remove(bonus);
				continue;
			}
			
		}
		
		return bonuses;
	}
	
	private void useBonus(Bonus bonus, User user) {
		
		UserStatistics stats = user.getUserStatistics();
		Set<Character> userCharacters = user.getCharacterSet();
		List<UserEnhancer> userEnhancers = user.getEnhancerList();
		Set<Group> userGroups = user.getGroupSet();
		
		Integer acorns = bonus.getAcorns();
		Integer goldAcorns = bonus.getGoldAcorns();
		Character bonusCharacter = bonus.getCharacter();
		Enhancer bonusEnhancer = bonus.getEnhancer();
		Group group = bonus.getGroup();
		if (acorns != null) {
			stats.setAcorns(stats.getAcorns() + acorns);
		}
		if (goldAcorns != null) {
			stats.setGoldAcorns(stats.getGoldAcorns() + goldAcorns);
		}
		user.setUserStatistics(stats);
		
		if (bonusCharacter != null) {
			userCharacters.add(bonusCharacter);
			user.setCharacterSet(userCharacters);
		}
		if (bonusEnhancer != null) {
			boolean hasEnhancer = false;
			for (UserEnhancer ue: userEnhancers) {
				if (ue.getEnhancer().getId() == bonusEnhancer.getId() 
						&& ue.getUser().getUsername().equals(user.getUsername())) {
					hasEnhancer = true;
					break;
				}
			}
			if (!hasEnhancer) {
				UserEnhancer userEnh = new UserEnhancer(bonusEnhancer, user, Status.BOUGHT, new Date());
				userEnhancers.add(userEnh);
				user.setEnhancerList(userEnhancers);
				
			}
			
		}
		
		if (group != null) {
			userGroups.add(group);
			user.setGroupSet(userGroups);
		}
		
		List<UserBonus> userBonusList = user.getBonusList();
		UserBonus userBonus = new UserBonus(bonus, user, Status.BOUGHT, new Date());
		userBonusList.add(userBonus);
		
		userRepository.updateUser(user);
	}

	

	
}
