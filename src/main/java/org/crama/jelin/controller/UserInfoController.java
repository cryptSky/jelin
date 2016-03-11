package org.crama.jelin.controller;

import java.io.IOException;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserInfo;
import org.crama.jelin.model.UserStatistics;
import org.crama.jelin.service.UserInfoService;
import org.crama.jelin.service.UserService;
import org.crama.jelin.service.impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserInfoController {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	
	@RequestMapping(value="/api/user/info", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody UserInfo getUserInfo(@RequestParam(required = false) Integer user) {
		
		User userObj = null;
		if (user == null) {
			userObj = userService.getPrincipal();
		}
		else {
			userObj = userService.getUser(user);
		}
		
    	UserInfo userInfo = userInfoService.getUserInfo(userObj);
		return userInfo;
    }
	
	@RequestMapping(value="/api/user/info", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public void updateUserInfo(@RequestBody UserInfo userInfo) {
		User user = userService.getPrincipal();
		
    	userInfoService.updateUserInfo(user, userInfo);
		
    }
	
	@RequestMapping(value="/api/user/info/image", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody String getUserAvatar() {
		User user = userService.getPrincipal();
		UserInfo userInfo = user.getUserInfo();
		if (userInfo == null) {
			return null;
		}
		return userInfo.getAvatar();
    	
    }
	
	@RequestMapping(value="/api/user/info/image", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public void uploadAvatar(@RequestParam(value="avatar", required=true) MultipartFile avatar) throws GameException {
		User user = userService.getPrincipal();
		
		userInfoService.validateFile(avatar);
		
		try {
			userInfoService.uploadAvatar(avatar, user);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
    }
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		logger.error(ge.getMessage());
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());
		
        return re;
    }
	
}
