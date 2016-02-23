package org.crama.jelin.service.impl;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;
import org.crama.jelin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

public class SocialSignInAdapter implements SignInAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(SocialSignInAdapter.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		logger.info("Social sign in. User id: " + userId);
		User user = userService.getUser(Integer.parseInt(userId));
		UserModel userModel = userService.getUserModel(user.getUsername());
	    Authentication authentication = createAuthentication(userModel);
	    
	    return null;
	}

	private Authentication createAuthentication(UserModel user) {
		//List<GrantedAuthority> authorities = UserDetailsServiceImpl.createAuthorities(user);
		org.springframework.security.core.userdetails.User springSecurityUser = userDetailsService.buildUserFromUserEntity(user);
	    Authentication authentication = new UsernamePasswordAuthenticationToken(
	    		springSecurityUser, user.getPassword(), springSecurityUser.getAuthorities());
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    return authentication;
	}
}
