package org.crama.jelin.service;

import java.util.List;
import java.util.Set;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Character;
import org.crama.jelin.model.Enhancer;
import org.crama.jelin.model.User;

public interface CharacterService {

	List<Character> getPublicCharacters();

	boolean saveUserCharacter(int character, User user) throws GameException;

	boolean saveCurrentCharacter(int character, User user);

	List<Character> getCharactersForSale(User user);

	List<Enhancer> getEnhancerList(User user);

	boolean buyCharacter(int character, User user) throws GameException;

	boolean buyEnhancer(int enhancer, User user) throws GameException;

	Character getCurrentCharacter(User user) throws GameException;

	Set<Character> getUserCharacters(User user);

}
