package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.Character;
import org.crama.jelin.model.Enhancer;
import org.crama.jelin.model.User;

public interface CharacterService {

	List<Character> getPublicCharacters();

	boolean saveUserCharacter(int character, User user);

	boolean saveCurrentCharacter(int character, User user);

	List<Character> getCharactersForSale(User user);

	List<Enhancer> getEnhancerList(User user);

}
