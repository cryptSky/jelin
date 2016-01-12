package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.exception.ErrorMessagesStorage;
import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Constants.NetStatus;
import org.crama.jelin.model.Constants.ProcessStatus;
import org.crama.jelin.model.Constants.UserType;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.GameBot;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;
import org.crama.jelin.model.UserRole;
import org.crama.jelin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserModel getUserModel(String username) {
		
		return userRepository.getUserModel(username);
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
			/*Set<UserRole> userRole = new HashSet<UserRole>();
			UserRole role = userRepository.getUserRole(UserRole.ROLE_USER);
			//UserRole role = new UserRole();
			//role.setRole(UserRole.ROLE_USER);
			userRole.add(role);
			model.setRoles(userRole);
			
			Set<UserRole> roles = model.getRoles();
			for (UserRole r: roles) {
				System.out.println(r);
			}*/
			UserRole role = userRepository.getUserRole(UserRole.ROLE_USER);
			model.setRole(role);
			
			userRepository.saveUserModel(model);
			//userRepository.saveUserRoles(model);
			User newUser = new User(model.getUsername(), model.getEmail());
			newUser.setType(UserType.HUMAN);
			newUser.setNetStatus(NetStatus.ONLINE);
			newUser.setProcessStatus(ProcessStatus.FREE);
			userRepository.saveUser(newUser);
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
        	throw new GameException(101, ErrorMessagesStorage.ERROR_101.getMessage());
        	
        }
	}

	@Override
	public boolean checkUserStatusIsEquals(User creator, ProcessStatus ps) throws GameException {
		if (!creator.getProcessStatus().equals(ps)) {
        	System.out.println("User is in status: " + creator.getProcessStatus());
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
	

}
