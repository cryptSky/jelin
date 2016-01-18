package org.crama.jelin.controller;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserInfo;
import org.crama.jelin.service.UserDetailsServiceImpl;
import org.crama.jelin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@RequestMapping(value="/api/user/info", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody UserInfo getUserInfo() {
		User user = userDetailsService.getPrincipal();
    	UserInfo userInfo = userInfoService.getUserInfo(user);
		return userInfo;
    }
	
	@RequestMapping(value="/api/user/info", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public void updateUserInfo(@RequestBody UserInfo userInfo) {
		User user = userDetailsService.getPrincipal();
		userInfo.setUser(user);
    	userInfoService.updateUserInfo(userInfo);
		
    }
	
}
