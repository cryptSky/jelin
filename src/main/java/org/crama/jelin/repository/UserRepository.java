package org.crama.jelin.repository;


import java.util.List;

import org.crama.jelin.model.Constants.Language;
import org.crama.jelin.model.Constants.NetStatus;
import org.crama.jelin.model.Constants.Readiness;
import org.crama.jelin.model.Game;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;
import org.crama.jelin.model.UserRole;


public interface UserRepository {

	UserModel getUserModel(String username);

	void saveUserModel(UserModel model);

	void saveUser(User user);

	//void saveUserRoles(UserModel model);

	UserRole getUserRole(String roleUser);

	UserModel getUserModelEmail(String email);

	User getUserByUsername(String username);
	
	List<User> getUsersShadowAndFree();
	
	List<User> getUsersOnlineAndFree();
	
	List<User> getUsersOnlineAndCalling(int exceptUserID);

	void updateUser(User user);

	User getUser(int userId);

	List<User> getAllUsers();
	
	int getMaxUserId();

	void updateAllUsersNetStatus(User user, NetStatus status);

	void updateNetStatus(User user, NetStatus s);

	User getByEmailAddress(String email);
	
	List<User> getPlayersNotWithReadiness(Readiness readiness, Game game);

	void lock(User user);

	List<User> getAllBots();
	
}
