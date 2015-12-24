package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Character;

public interface CharacterRepository {

	List<Character> getStandardCharacters();

	Character getCharacter(int character);

}
