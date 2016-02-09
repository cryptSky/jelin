package org.crama.jelin.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserInfo;
import org.springframework.web.multipart.MultipartFile;

public interface UserInfoService {

	UserInfo getUserInfo(User user);

	void updateUserInfo(UserInfo userInfo);

	void uploadAvatar(MultipartFile avatar, User user) throws FileNotFoundException, IOException;

	void validateFile(MultipartFile file) throws GameException;

}
