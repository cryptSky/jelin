package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;

public interface UserService {

	UserModel getUserModel(String username);

	boolean checkUsername(String username);

	boolean saveUser(UserModel model);

	boolean checkEmail(String email);

	User getUserByUsername(String username);
	
	User getUserById(int id);
	
	List<Integer> getUserIdsShadowAndFree();
	
	List<Integer> getUserIdsOnlineAndFree();
	
	List<Integer> getUserIdsOnlineAndFreeNotRecentlyInvolved();

	void updateUserProcessStatus(User creator, String processStatus);

}
