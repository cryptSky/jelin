package org.crama.jelin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Difficulty;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameBot;
import org.crama.jelin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("opponentSearchService")
public class OpponentSearchServiceImpl implements OpponentSearchService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserInterestsService userInterestsService;
	
	@Autowired
	private CategoryService categoryService;
	
	
	@Override
	public User findOppenent(Game game) {
		
		// stage 1: search users who are shadow and free
		List<User> shadowAndFree = userService.getUsersShadowAndFree(); 
		User opponent = this.findUser(shadowAndFree, game.getTheme(), game.getDifficulty(), true);
		if (opponent != null){
			return opponent;
		}		
		
		// stage2: search users who are online and free, and haven't played a lot
		List<User> onlineAndFree = userService.getUsersOnlineAndFreeNotRecentlyInvolved();
		opponent = this.findUser(onlineAndFree, game.getTheme(), game.getDifficulty(), false);
		if (opponent != null){
			return opponent;
		}
		
		// stage3: search users who are online and free
		onlineAndFree = userService.getUsersOnlineAndFree(); 
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
	
	private User findUser(List<User> users, Category category, Difficulty difficulty, boolean random)
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
	
	private User selectOponent(List<User> users, boolean random)
	{
		if (random == true) {
			
			Random randomGenerator = new Random();
			int index = randomGenerator.nextInt(users.size());
			User opponent = users.get(index);
		
			return opponent;
			
		} else {
			
			Collections.sort(users, new Comparator<User>(){
				   public int compare(User u1, User u2){
				      return u1.getLastGameTime().compareTo(u2.getLastGameTime());
				   }
				});
			
			User opponent = users.get(0);
			return opponent;
		}
	}
	
	private User findUserByTopicAndDifficulty(List<User> users, Category category, Difficulty difficulty, boolean random)
	{
		List<User> usersWithTopic = userInterestsService
				.getUsersByThemeAndDifficultyFromUsers(users, category, difficulty);
		
		if (usersWithTopic != null && usersWithTopic.size() > 0)
		{
			return selectOponent(usersWithTopic, random);
		}
		else
		{
			return null;
		}
	}
	
	private User findUserBySubTopicAndDifficulty(List<User> users, Category category, Difficulty difficulty, boolean random)
	{
		List<User> usersWithSubTopics = new ArrayList<User>();
		List<Category> childTopics = categoryService.getChildThemesByParent(category);
		
		if (childTopics != null)
		{
			for (Category childTopic : childTopics) {
				List<User> usersWithSubTopic = userInterestsService
						.getUsersByThemeAndDifficultyFromUsers(users, childTopic, difficulty);
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
