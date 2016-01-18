package org.crama.jelin.service;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserInfo;

public interface UserInfoService {

	UserInfo getUserInfo(User user);

	void updateUserInfo(UserInfo userInfo);

}
