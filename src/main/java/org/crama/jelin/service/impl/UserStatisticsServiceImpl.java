package org.crama.jelin.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
import org.crama.jelin.service.UserStatisticsService;
import org.crama.jelin.util.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userStatisticsService")
public class UserStatisticsServiceImpl implements UserStatisticsService {

	@Autowired
	private UserStatisticsRepository userStatisticsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	//TODO complete
	@Override
	public List<RatingJson> getRating(User user, int time, int people) {
		
		List<UserStatistics> userStatistics = null;
		
		switch (time) {
			case 0: 
				
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				//all
				if (people == 0) {
					userStatistics = userStatisticsRepository.getAllUsersStatistics();
				}
				//friends
				else if (people == 1) {
					
				}
				break;
		}
		
		Collections.sort(userStatistics);
		List<RatingJson> ratings = new ArrayList<RatingJson>();
		RatingJson rating = null;
		for (int i = 0; i < userStatistics.size(); i++) {
			UserStatistics s = userStatistics.get(i);
			rating = new RatingJson(i + 1, s.getPoints(), 
					s.getUser().getUserInfo().getAvatar(), s.getUser().getUsername());
			ratings.add(rating);
		}
		
		
		return ratings;
	}


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
			int finalAcorns = acorns + acorns * bonusAcorns / 100;
			int finalPoints = points + points * bonusPoints / 100;
			 
			System.out.println("Acorns: " + acorns + ", finalAcorns: " + finalAcorns);
			System.out.println("Points: " + points + ", finalPoints: " + finalPoints);
			
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
