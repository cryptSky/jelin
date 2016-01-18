package org.crama.jelin.service;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserInfo;
import org.crama.jelin.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Override
	public UserInfo getUserInfo(User user) {
		
		return userInfoRepository.getUserInfo(user);
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		userInfoRepository.updateUserInfo(userInfo);
	}

}
