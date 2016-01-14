package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Character;
import org.crama.jelin.model.Enhancer;
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
			+ "WHERE special = false AND acrons = 0 AND goldAcrons = 0";
	private static final String GET_ALL_CHARACTERS_FOR_MONEY = "FROM Character "
			+ "WHERE acrons <> 0 OR goldAcrons <> 0";
	private static final String GET_CHARACTER = "FROM Character "
			+ "WHERE id = :id";
	private static final String GET_ALL_ENHANCERS = "FROM Enhancer ";
	
	
	@Override
	public List<Character> getStandardCharacters() {
		
		Query query = sessionFactory.getCurrentSession().createQuery(GET_STANDARD_CHARACTERS);
		@SuppressWarnings("unchecked")
		List<Character> characterList = query.list();
		return characterList;
	}
	
	@Override
	public List<Character> getCharactersForMoney() {
		
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_CHARACTERS_FOR_MONEY);
		@SuppressWarnings("unchecked")
		List<Character> characterList = query.list();
		return characterList;
	}

	@Override
	public Character getCharacter(int characterId) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_CHARACTER);
		query.setParameter("id", characterId);
		Character character = (Character)query.uniqueResult();
		return character;
	}

	@Override
	public List<Enhancer> getAllEnhancers() {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_ENHANCERS);
		@SuppressWarnings("unchecked")
		List<Enhancer> enhancerList = (List<Enhancer>)query.list();
		return enhancerList;
	}

	@Override
	public Enhancer getEnhancer(int enhancer) {
		return (Enhancer)sessionFactory.getCurrentSession().get(Enhancer.class, enhancer);
	}
	
}
