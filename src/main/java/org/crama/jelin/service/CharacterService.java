package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.Character;
import org.crama.jelin.model.User;

public interface CharacterService {

	List<Character> getPublicCharacters();

	boolean saveUserCharacter(String character, User user);

	boolean saveCurrentCharacter(String character, User user);

}