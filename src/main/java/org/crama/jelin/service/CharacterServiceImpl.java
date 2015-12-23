package org.crama.jelin.service;

import java.util.List;
import java.util.Set;

import org.crama.jelin.model.Character;
import org.crama.jelin.model.User;
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
	public boolean saveUserCharacter(String character, User user) {
		Character userCharacter = characterRepository.getCharacter(character);
		if (userCharacter == null) return false;
		System.out.println(userCharacter);
		Set<Character> userCharacterSet = user.getCharacterSet();
		userCharacterSet.add(userCharacter);
		userRepository.updateUser(user);
		return true;
	}

	@Override
	public boolean saveCurrentCharacter(String character, User user) {
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

}
