package org.crama.jelin.repository;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserInfo;

public interface UserInfoRepository {

	UserInfo getUserInfo(User user);

	void updateUserInfo(UserInfo userInfo);

}
