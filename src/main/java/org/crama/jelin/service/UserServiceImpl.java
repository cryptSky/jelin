package org.crama.jelin.service;

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
			userRepository.saveUser(new User(model.getUsername(), model.getEmail()));
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

}
