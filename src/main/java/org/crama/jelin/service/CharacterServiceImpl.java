package org.crama.jelin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Character;
import org.crama.jelin.model.Constants.Status;
import org.crama.jelin.model.Enhancer;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserEnhancer;
import org.crama.jelin.repository.CharacterRepository;
import org.crama.jelin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("characterService")
public class CharacterServiceImpl implements CharacterService {

	@Autowired
	private CharacterRepository characterRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<Character> getPublicCharacters() {
		return characterRepository.getStandardCharacters();
	}

	@Override
	public boolean saveUserCharacter(int character, User user) throws GameException {
		Character userCharacter = characterRepository.getCharacter(character);
		if (userCharacter == null) {
			throw new GameException(302, "There is no character with id: " + character);
		}
		System.out.println(userCharacter);
		Set<Character> userCharacterSet = user.getCharacterSet();
		userCharacterSet.add(userCharacter);
		userRepository.updateUser(user);
		return true;
	}

	@Override
	public boolean saveCurrentCharacter(int character, User user) {
		Character userCharacter = characterRepository.getCharacter(character);
		if (userCharacter == null) return false;
		Set<Character> userCharacterSet = user.getCharacterSet();
		
		for (Character c: userCharacterSet) {
			if (c.equals(userCharacter)) {
				
				user.setChoosenCharacter(userCharacter);
				userRepository.updateUser(user);
				return true;
				
			}
		}
		//character is not in user set
		return false;
		
	}

	@Override
	public List<Character> getCharactersForSale(User user) {
		List<Character> characters = characterRepository.getCharactersForMoney();
		Set<Character> userCharacters = user.getCharacterSet();
		for (Character c: new ArrayList<Character>(characters)) {
			
			System.out.println(c);
			
			for (Character uc: userCharacters) {
				if (c.equals(uc)) {
					characters.remove(c);
				}
			}
		}
		return characters;
	}

	@Override
	public List<Enhancer> getEnhancerList(User user) {
		List<Enhancer> enhancerList = characterRepository.getAllEnhancers();
		Set<Character> userCharacters = user.getCharacterSet();
		List<UserEnhancer> userEnhancer = user.getEnhancerList();
		enhancerLoop: for (Enhancer enh: new ArrayList<Enhancer>(enhancerList)) {
			for (UserEnhancer ue: userEnhancer) { 
				//user already has this enhancer
				if (enh.equals(ue.getEnhancer())) {
					enhancerList.remove(enh);
					continue enhancerLoop;
				}
			}
			if (enh.getCharacter() == null) {
				continue enhancerLoop;
			}
			if (!userCharacters.contains(enh.getCharacter())) {
				enhancerList.remove(enh);
			}
			
		}
		return enhancerList;
	}

	@Override
	public boolean buyCharacter(int character, User user) throws GameException {
		Character userCharacter = characterRepository.getCharacter(character);
		if (userCharacter == null) {
			throw new GameException(306, "There is no character with id: " + character);
		}
		System.out.println(userCharacter);
		Set<Character> userCharacterSet = user.getCharacterSet();
		boolean added = userCharacterSet.add(userCharacter);
		if (!added) {
			throw new GameException(306, "You already have this character");
		}
		int acorns = userCharacter.getAcrons();
		int goldAcorns = userCharacter.getGoldAcrons();
		if (acorns != 0) {
			int acornsLeft = user.getAcorns() - acorns;
			if (acornsLeft < 0) {
				throw new GameException(306, "You have not enough acorns to buy character");
			}
			user.setAcorns(acornsLeft);
		}
		else if (goldAcorns != 0) {
			int acornsLeft = user.getGoldAcorns() - goldAcorns;
			if (acornsLeft < 0) {
				throw new GameException(306, "You have not enough gold acorns to buy character");
			}
			user.setGoldAcorns(acornsLeft);
		}
		
		userRepository.updateUser(user);
		return true;
	}

	@Override
	public boolean buyEnhancer(int enhancerId, User user) throws GameException {
		Enhancer enhancerObj = characterRepository.getEnhancer(enhancerId);
		if (enhancerObj == null) {
			throw new GameException(307, "There is no enhancer with id: " + enhancerId);
		}
		System.out.println(enhancerObj);
		
		List<UserEnhancer> userEnhancerList = user.getEnhancerList(); 
		for (UserEnhancer ue: userEnhancerList) {
			if (ue.getEnhancer().equals(enhancerObj)) {
				throw new GameException(307, "You already have this enhancer");
			}
		}
		int acorns = enhancerObj.getAcorns();
		int goldAcorns = enhancerObj.getGoldAcorns();
		if (acorns != 0) {
			int acornsLeft = user.getAcorns() - acorns;
			if (acornsLeft < 0) {
				throw new GameException(307, "You have not enough acorns to buy enhancer");
			}
			user.setAcorns(acornsLeft);
		}
		else if (goldAcorns != 0) {
			int acornsLeft = user.getGoldAcorns() - goldAcorns;
			if (acornsLeft < 0) {
				throw new GameException(307, "You have not enough gold acorns to buy enhancer");
			}
			user.setGoldAcorns(acornsLeft);
		}
		UserEnhancer newUserEnhancer = new UserEnhancer(enhancerObj, user, Status.BOUGHT, new Date());
		userEnhancerList.add(newUserEnhancer);
		
		userRepository.updateUser(user);
		
		return true;
	}

}
