package org.crama.jelin.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserInfo;
import org.crama.jelin.repository.ImageRepository;
import org.crama.jelin.repository.UserInfoRepository;
import org.crama.jelin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private ImageRepository imageRepository; 
	
	@Override
	public UserInfo getUserInfo(User user) {
		
		return userInfoRepository.getUserInfo(user);
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		userInfoRepository.updateUserInfo(userInfo);
	}

	@Override
	public void uploadAvatar(MultipartFile avatar, User user) throws FileNotFoundException, IOException {
		
		String avatarPath = imageRepository.uploadImage(avatar);
		
		UserInfo userInfo = user.getUserInfo(); 
		userInfo.setAvatar(avatarPath);
		userInfoRepository.updateUserInfo(userInfo);
		
	}
	
	@Override
    public void validateFile(MultipartFile file) throws GameException {

       if(file.isEmpty() || file.getSize()==0)
    	   throw new GameException(110, "Please select a file");
       if(!(file.getContentType().toLowerCase().equals("image/jpg") 
            || file.getContentType().toLowerCase().equals("image/jpeg") 
            || file.getContentType().toLowerCase().equals("image/png"))){
           throw new GameException(110, "jpg/png file types are only supported");
       }
    }

}
