package org.crama.jelin.service.impl;

import java.util.Date;
import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Constants.NetStatus;
import org.crama.jelin.model.Constants.ProcessStatus;
import org.crama.jelin.model.Constants.UserType;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameBot;
import org.crama.jelin.model.Settings;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserActivity;
import org.crama.jelin.model.UserDailyStats;
import org.crama.jelin.model.UserInfo;
import org.crama.jelin.model.UserModel;
import org.crama.jelin.model.UserRole;
import org.crama.jelin.model.UserStatistics;
import org.crama.jelin.repository.UserRepository;
import org.crama.jelin.service.MailService;
import org.crama.jelin.service.SettingsService;
import org.crama.jelin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private SettingsService settingsService;
	
	
	@Override
	public UserModel getUserModel(String username) {
		
		return userRepository.getUserModel(username);
	}

	@Override
	public User getPrincipal() {
		Authentication authentication = 
				SecurityContextHolder.getContext().getAuthentication();
		if ((authentication == null) || (!authentication.isAuthenticated())) {
		  
			return null;
		}
	  
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		User loginUser = getUserByUsername(userDetails.getUsername());
		return loginUser;
	  
	}
	
	@Override
	public boolean checkUsername(String username) {
		UserModel userModel = userRepository.getUserModel(username);
		if (userModel == null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean saveUser(UserModel model) {
		if (checkUsername(model.getUsername())) {
			
			UserRole role = userRepository.getUserRole(UserRole.ROLE_USER);
			model.setRole(role);
			
			userRepository.saveUserModel(model);
			User newUser = new User(model.getUsername(), model.getEmail());
			newUser.setType(UserType.HUMAN);
			newUser.setNetStatus(NetStatus.ONLINE);
			newUser.setProcessStatus(ProcessStatus.FREE);
			newUser.setRegisterDate(new Date());
			userRepository.saveUser(newUser);
			
			Settings settings = settingsService.getSettings();
			//TODO send email
			mailService.sendRegistrationEmail(newUser, settings);
			
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean checkEmail(String email) {
		UserModel userModel = userRepository.getUserModelEmail(email);
		if (userModel == null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public User getUserByUsername(String username) {
		
		return userRepository.getUserByUsername(username);
	}

	@Override
	public void updateUserProcessStatus(User creator, ProcessStatus status) {
		creator.setProcessStatus(status);
		userRepository.updateUser(creator);
	}

	@Override
	public List<User> getUsersShadowAndFree() {
		return userRepository.getUsersShadowAndFree();
	}

	@Override
	public List<User> getUsersOnlineAndFree() {
		return userRepository.getUsersOnlineAndFree();
	}

	@Override
	public List<User> getUsersOnlineAndCalling(User exceptUser) {
		return userRepository.getUsersOnlineAndCalling(exceptUser.getId());
	}

	@Override
	public User getUser(int userId) {
		
		return userRepository.getUser(userId);
	}

	@Override
	public void checkUserAuthorized(User creator) throws GameException {
		if (creator == null) {
        	throw new GameException(101, "User is not autheticated");
        	
        }
	}

	@Override
	public boolean checkUserStatusIsEquals(User creator, ProcessStatus ps) throws GameException {
		if (!creator.getProcessStatus().equals(ps)) {
        	return false; 
        
		}
		return true;
	}

	@Override
	public List<User> getAllUsers(User user) {
		List<User> all = userRepository.getAllUsers();
		all.remove(user);
		
		return all;
	}

	@Override
	public User createBot(Game game, GameBot bot) {
		User userBot = new User("", "");
				
		StringBuilder sb = new StringBuilder();
		int userNumber = game.getGameOpponents().size();
		sb.append("bot").append(game.getId()).append("_").append(userNumber);
		String name = sb.toString();
		String email = sb.append("@bot.com").toString();
		
		userBot.setUsername(name);
		userBot.setEmail(email);		
		
		userBot.setType(UserType.BOT);
		userBot.setBot(bot);
		userRepository.saveUser(userBot);
		
		return userBot;
	}

	@Override
	public void changeOthersNetStatus(User user, int status) throws GameException {
		NetStatus s = null;
		if (status == 0) {
			s = NetStatus.ONLINE;
		}
		else if (status == 1) {
			s = NetStatus.SHADOW;
		}
		else if (status == 2) {
			s = NetStatus.OFFLINE;
		}
		else {
			throw new GameException(112, "Wrong NetStatus value");
		}
		userRepository.updateAllUsersNetStatus(user, s);
		
	}

	@Override
	public void changeNetStatus(User user, int status) throws GameException {
		NetStatus s = null;
		if (status == 0) {
			s = NetStatus.ONLINE;
		}
		else if (status == 1) {
			s = NetStatus.SHADOW;
		}
		else if (status == 2) {
			s = NetStatus.OFFLINE;
		}
		else {
			throw new GameException(112, "Wrong NetStatus value");
		}
		userRepository.updateNetStatus(user, s);
		
	}

	@Override
	public void createUsersInfoAndStatistics() {
		List<User> allUsers = userRepository.getAllUsers();
		
		for (User u: allUsers) {
			UserInfo info = u.getUserInfo();
			UserStatistics stats = u.getUserStatistics();
			UserActivity activ = u.getUserActivity();
			UserDailyStats dailyStats = u.getUserDailyStats();
			if (info == null) {
				info = new UserInfo(u);
				u.setUserInfo(info);
			}
			if (stats == null) {
				stats = new UserStatistics(u);
				u.setUserStatistics(stats);
			}
			if (activ == null) {
				activ = new UserActivity(u);
				u.setUserActivity(activ);
			}
			if (dailyStats == null) {
				dailyStats = new UserDailyStats(u);
				u.setUserDailyStats(dailyStats);
			}
			userRepository.updateUser(u);
		}
	}

	@Override
	public void remindPassword(String access) throws GameException {
		
		UserModel userModel = userRepository.getUserModel(access);
		if (userModel == null) {
			userModel = userRepository.getUserModelEmail(access);
			if (userModel == null) {
				throw new GameException(103, "User with given username or email is not registered");
			}
		}
		Settings settings = settingsService.getSettings();
		//TODO send email
		mailService.remindPassword(userModel, settings);
		
	}
	
	

	

}
