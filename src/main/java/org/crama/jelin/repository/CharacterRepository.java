package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Character;
import org.crama.jelin.model.Enhancer;

public interface CharacterRepository {

	List<Character> getStandardCharacters();

	Character getCharacter(int character);

	List<Character> getCharactersForMoney();

	List<Enhancer> getAllEnhancers();

	Enhancer getEnhancer(int enhancer);

}
