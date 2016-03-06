package org.crama.jelin.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Constants.UserType;
import org.crama.jelin.model.Enhancer;
import org.crama.jelin.model.ScoreSummary;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserActivity;
import org.crama.jelin.model.UserDailyStats;
import org.crama.jelin.model.UserEnhancer;
import org.crama.jelin.model.UserStatistics;
import org.crama.jelin.model.json.RatingJson;
import org.crama.jelin.repository.UserRepository;
import org.crama.jelin.repository.UserStatisticsRepository;
import org.crama.jelin.service.RatingService;
import org.crama.jelin.service.UserStatisticsService;
import org.crama.jelin.util.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userStatisticsService")
public class UserStatisticsServiceImpl implements UserStatisticsService {

	private static final Logger logger = LoggerFactory.getLogger(UserStatisticsServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void updateDaysInGame(User user) {
		
		UserStatistics userStats = user.getUserStatistics();
		UserActivity userActivity = user.getUserActivity();
		Date lastEnterDate = userActivity.getLastLogin();
		if (lastEnterDate == null) {
			userStats.setDaysInGame(1);
		}
		else {
			LocalDateTime localEnterDate = DateConverter.toLocalDateTime(lastEnterDate);
			LocalDateTime now = LocalDateTime.now();
			long days = ChronoUnit.DAYS.between(now, localEnterDate);
			if (days == 1) {
				userStats.setDaysInGame(userStats.getDaysInGame() + 1);
			}
			else if (days == 0) {
				
			}
			else {
				userStats.setDaysInGame(1);
			}
			
		}
		user.setUserStatistics(userStats);
		userRepository.updateUser(user);
		
		
	}


	@Override
	public void buyGoldAcorns(User user, Integer acorns) {
		UserStatistics stats = user.getUserStatistics(); 
		stats.setGoldAcorns(stats.getGoldAcorns() + acorns);
		stats.setMoneySpent(stats.getMoneySpent() + acorns);
		user.setUserStatistics(stats);
		userRepository.updateUser(user);
	}


	@Override
	public void addInitiatedGame(User creator) {
		UserStatistics stats = creator.getUserStatistics();
		stats.setGamesInitiated(stats.getGamesInitiated() + 1);
		creator.setUserStatistics(stats);
		
		UserDailyStats dailyStats = creator.getUserDailyStats();
		dailyStats = checkDailyStatsDate(dailyStats);
		dailyStats.setGamesInitiated(dailyStats.getGamesInitiated() + 1);
		creator.setUserDailyStats(dailyStats);
		
		userRepository.updateUser(creator);
	}

	private UserDailyStats checkDailyStatsDate(UserDailyStats dailyStats) {
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		
		if (!fmt.format(dailyStats.getDate()).equals(fmt.format(new Date()))) {
			dailyStats.clear();
		}
		return dailyStats;
		
	}


	//save user points and acorns 
	//takes into account user enhancers
	//increase games stats
	@Override
	public void saveGameSummaryStats(List<ScoreSummary> summaries) {
		Collections.sort(summaries);
		int winnerPoints = summaries.get(0).getScore(); 
		int loserPoints = summaries.get(summaries.size() - 1).getScore();
		
		for (ScoreSummary scoreSummary: summaries) {
			User user = scoreSummary.getUser();
			if (user.getType().equals(UserType.BOT)) {
				continue;
			}
			
			int userAcorns = user.getUserStatistics().getAcorns();
			int userPoints = user.getUserStatistics().getPoints();
			
			int acorns = scoreSummary.getAcrons();
			int points = scoreSummary.getScore();
			
			List<UserEnhancer> userEnhancerList = user.getEnhancerList();
			
			int bonusAcorns = 0;
			int bonusPoints = 0;
			for (UserEnhancer userEnhancer: userEnhancerList) {
				Enhancer enh = userEnhancer.getEnhancer();
				int acornsPercent = enh.getAcornsPercent();
				int pointsPercent = enh.getPointsPercent();
				bonusAcorns += acornsPercent;
				bonusPoints += pointsPercent;
						
			}
			int finalAcorns = userAcorns + acorns * bonusAcorns / 100;
			int finalPoints = userPoints + points * bonusPoints / 100;
			 
			logger.debug("Acorns: " + acorns + ", finalAcorns: " + finalAcorns);
			logger.debug("Points: " + points + ", finalPoints: " + finalPoints);
			
			UserStatistics userStats = user.getUserStatistics();
			userStats.setAcorns(finalAcorns);
			userStats.setPoints(finalPoints);
			
			//all games, wins, lost, middle
			userStats.setGamesPlayed(userStats.getGamesPlayed() + 1);
			
			if (points == winnerPoints) {
				userStats.setGamesWon(userStats.getGamesWon() + 1);
			}
			else if (points == loserPoints) {
				userStats.setGamesLastPlace(userStats.getGamesLastPlace() + 1);
			}
			else {
				userStats.setGamesMiddlePlace(userStats.getGamesMiddlePlace() + 1);
			}
			
			user.setUserStatistics(userStats);
			
			//update daily statistics
			UserDailyStats dailyStats = user.getUserDailyStats();
			dailyStats = checkDailyStatsDate(dailyStats);
			dailyStats.setGamesPlayed(dailyStats.getGamesPlayed() + 1);
			dailyStats.setPoints(dailyStats.getPoints() + points);
			user.setUserDailyStats(dailyStats);
			
			userRepository.updateUser(user);
		}
				
		
	}


	@Override
	public void increaseSocialInvites(User user) {
		
		UserStatistics stats = user.getUserStatistics();
		stats.setSmmInvites(stats.getSmmInvites() + 1);
		user.setUserStatistics(stats);
		
		//update daily statistics
		UserDailyStats dailyStats = user.getUserDailyStats();
		dailyStats = checkDailyStatsDate(dailyStats);
		dailyStats.setSmmInvites(dailyStats.getSmmInvites() + 1);
		user.setUserDailyStats(dailyStats);
		
		userRepository.updateUser(user);
		
	}


	@Override
	public void increaseSocialShares(User user) {
		
		UserStatistics stats = user.getUserStatistics();
		stats.setSmmShares(stats.getSmmShares() + 1);
		user.setUserStatistics(stats);
		
		//update daily statistics
		UserDailyStats dailyStats = user.getUserDailyStats();
		dailyStats = checkDailyStatsDate(dailyStats);
		dailyStats.setSmmShares(dailyStats.getSmmShares() + 1);
		user.setUserDailyStats(dailyStats);
		
		userRepository.updateUser(user);
		
	}
	
	
	
}
