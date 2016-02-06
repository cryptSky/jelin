package org.crama.jelin.controller;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserInfo;
import org.crama.jelin.model.UserStatistics;
import org.crama.jelin.service.UserInfoService;
import org.crama.jelin.service.UserService;
import org.crama.jelin.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

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
		userInfo.setUser(user);
    	userInfoService.updateUserInfo(userInfo);
		
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
    public void updateUserAvatar(@RequestParam String avatar) {
		User user = userService.getPrincipal();
		UserInfo userInfo = user.getUserInfo(); 
		userInfo.setAvatar(avatar);
		userInfoService.updateUserInfo(userInfo);
		
    }
	
	
	
}
