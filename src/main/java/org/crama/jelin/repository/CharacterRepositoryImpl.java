package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Character;
import org.crama.jelin.model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("characterRepository")
public class CharacterRepositoryImpl implements CharacterRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final String GET_STANDARD_CHARACTERS = "FROM Character "
			+ "WHERE special = false";
	private static final String GET_CHARACTER = "FROM Character "
			+ "WHERE id = :id";
	
	@Override
	public List<Character> getStandardCharacters() {
		
		Query query = sessionFactory.getCurrentSession().createQuery(GET_STANDARD_CHARACTERS);
		@SuppressWarnings("unchecked")
		List<Character> characterList = query.list();
		return characterList;
	}

	@Override
	public Character getCharacter(String characterId) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_CHARACTER);
		query.setParameter("id", characterId);
		Character character = (Character)query.uniqueResult();
		return character;
	}
	
}
