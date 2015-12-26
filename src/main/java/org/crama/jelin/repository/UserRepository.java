package org.crama.jelin.repository;


import java.util.List;

import org.crama.jelin.model.ProcessStatus;
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
	
	List<Integer> getUserIdsShadowAndFree();
	
	List<Integer> getUserIdsOnlineAndFree();
	
	List<Integer> getUserIdsOnlineAndFreeNotRecentlyInvolved();

	void updateUser(User user);

	User getUser(int userId);

	ProcessStatus getProcessStatus(String inviting);

}
