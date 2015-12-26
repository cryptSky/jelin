package org.crama.jelin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.*;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameBot;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserInterests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("opponentSearchService")
public class OpponentSearchServiceImpl implements OpponentSearchService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserInterestsService userInterestsService;
	
	@Override
	public User findOppenent(User creator, Game game) {
		
		// stage 1: search users who are shadow and free
		List<Integer> shadowAndFree = userService.getUserIdsShadowAndFree(); 
		User opponent = this.findUser(shadowAndFree, game.getTheme(), game.getDifficulty(), true);
		if (opponent != null){
			return opponent;
		}		
		
		// stage2: search users who are online and free, and haven't played a lot
		List<Integer> onlineAndFree = userService.getUserIdsOnlineAndFreeNotRecentlyInvolved();
		opponent = this.findUser(onlineAndFree, game.getTheme(), game.getDifficulty(), false);
		if (opponent != null){
			return opponent;
		}
		
		// stage3: search users who are online and free
		onlineAndFree = userService.getUserIdsOnlineAndFree(); 
		opponent = this.findUser(onlineAndFree, game.getTheme(), game.getDifficulty(), true);
		if (opponent != null){
			return opponent;
		}
		
		return null;
	}
	
	@Override
	public GameBot getBot(Game game) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private User findUser(List<Integer> users, Category category, Difficulty difficulty, boolean random)
	{
		User user = findUserByTopicAndDifficulty(users, category, difficulty, random);
		if (user != null)
		{
			return user;
		}
		user = findUserBySubTopicAndDifficulty(users, category, difficulty, random);
		if (user != null)
		{
			return user;
		}
		Category parent = category.getParent();
		user = findUserByTopicAndDifficulty(users, parent, difficulty, random);
		if (user != null)
		{
			return user;
		}
		
		return null;
	}
	
	private User selectOponent(List<Integer> users, boolean random)
	{
		if (random == true) {
			
			Random randomGenerator = new Random();
			int index = randomGenerator.nextInt(users.size());
			int userId = users.get(index);
		
			User opponent = userService.getUser(userId);
			return opponent;
			
		} else {
			User opponent = userService.getUser(users.get(0));
			return opponent;
		}
	}
	
	private User findUserByTopicAndDifficulty(List<Integer> users, Category category, Difficulty difficulty, boolean random)
	{
		List<Integer> usersWithTopic = userInterestsService
				.getUserIdsByThemeAndDifficultyFromUsers(users, category, difficulty);
		
		if (usersWithTopic != null && usersWithTopic.size() > 0)
		{
			return selectOponent(usersWithTopic, random);
		}
		else
		{
			return null;
		}
	}
	
	private User findUserBySubTopicAndDifficulty(List<Integer> users, Category category, Difficulty difficulty, boolean random)
	{
		List<Integer> usersWithSubTopics = new ArrayList<Integer>();
		List<Category> childTopics = category.getChildCategories();
		
		if (childTopics != null)
		{
			for (Category childTopic : childTopics) {
				List<Integer> usersWithSubTopic = userInterestsService
						.getUserIdsByThemeAndDifficultyFromUsers(users, childTopic, difficulty);
				if (usersWithSubTopic != null && usersWithSubTopic.size() > 0) {
					usersWithSubTopics.addAll(usersWithSubTopic);
				}
			}
			
			if (usersWithSubTopics.size() > 0)
			{
				return selectOponent(usersWithSubTopics, random);
			}
			else
			{
				return null;
			}
			
		} else {
			return null;
		}
			
	}
		

}
