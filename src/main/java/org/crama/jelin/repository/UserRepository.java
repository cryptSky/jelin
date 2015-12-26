package org.crama.jelin.repository;

import java.util.List;

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
	
	User getUserById(int id);
	
	List<Integer> getUserIdsShadowAndFree();
	
	List<Integer> getUserIdsOnlineAndFree();
	
	List<Integer> getUserIdsOnlineAndFreeNotRecentlyInvolved();

	void updateUser(User user);

}
