package org.crama.jelin.repository;

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

}
