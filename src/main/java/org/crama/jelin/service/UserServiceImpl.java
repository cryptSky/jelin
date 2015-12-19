package org.crama.jelin.service;

import org.crama.jelin.model.UserModel;
import org.crama.jelin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserModel getUserModel(String email) {
		
		return userRepository.getUserModel(email);
	}

}
