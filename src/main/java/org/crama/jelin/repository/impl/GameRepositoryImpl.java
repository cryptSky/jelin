package org.crama.jelin.repository.impl;

import java.util.List;

import org.crama.jelin.model.Answer;
import org.crama.jelin.model.Constants.Readiness;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameBot;
import org.crama.jelin.model.GameRound;
import org.crama.jelin.model.QuestionResult;
import org.crama.jelin.model.ScoreSummary;
import org.crama.jelin.repository.AnswerRepository;
import org.crama.jelin.repository.GameRepository;
import org.crama.jelin.repository.GameRoundRepository;
import org.crama.jelin.repository.QuestionResultRepository;
import org.crama.jelin.repository.ScoreSummaryRepository;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("gameRepository")
public class GameRepositoryImpl implements GameRepository {

	private static final Logger logger = LoggerFactory.getLogger(GameRepositoryImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private GameRoundRepository gameRoundRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionResultRepository questionResultRepository;
	
	@Autowired
	private ScoreSummaryRepository scoreSummaryRepository;
	

	@Override
	@Transactional
	public void updateGame(Game game) {
		
		Session session = sessionFactory.getCurrentSession();	
		session.update(game);
	}

	@Override
	@Transactional
	public Readiness getReadiness(Game game) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Game.class);
		criteria = criteria.add(Restrictions.eq("id", game.getId()))
				           .setProjection(Projections.property("readiness"));
		Readiness readiness = (Readiness) criteria.uniqueResult();
		
		return readiness;
		
	}
	
	@Override
	public Game reloadGame(Game game) {
		Session session = sessionFactory.getCurrentSession();
		Game result = (Game) session.get(Game.class, game.getId());
				
		return result;
	}
	
	@Override
	@Transactional
	public void cleanUpGame(Game game)
	{
		try
		{
			Session session = sessionFactory.getCurrentSession();
		//	game.setRound(null);
		//	updateGame(game);
		//	session.flush();
				
		//	session.delete(game);
			session.flush();
		
		/*
		List<Answer> answers = answerRepository.getAnswersByGame(game);
		for (Answer ans: answers)
		{
			ans.setQuestion(null);
			answerRepository.update(ans);
			session.flush();
			session.delete(ans);
		}
		
		List<QuestionResult> qresults = questionResultRepository.getQuestionResultsByGame(game);
		for (QuestionResult qr: qresults)
		{
			qr.setQuestion(null);
			questionResultRepository.update(qr);
			session.flush();
			session.delete(qr);
		}
		
		List<GameRound> rounds = gameRoundRepository.getAllRoundsByGame(game);
		for (GameRound round: rounds)
		{
			session.delete(round);
		}
		
		List<ScoreSummary> summaries = scoreSummaryRepository.getSummaryByGame(game);
		for (ScoreSummary summary: summaries)
		{
			session.delete(summary);
		}
				
		*/
		
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
	}

	@Override
	public void lock(Game game) {
		Session session = sessionFactory.getCurrentSession();	
		session.buildLockRequest(LockOptions.NONE).lock(game);
		
	}

	@Override
	@Transactional
	public void setReadiness(Game game, Readiness readiness) {
		game.setReadiness(readiness);
		Session session = sessionFactory.getCurrentSession();	
		session.update(game);
		session.flush();
		
	}	

}
