package org.crama.jelin.controller;

import java.util.Set;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.User;
import org.crama.jelin.service.UserDetailsServiceImpl;
import org.crama.jelin.service.UserFriendService;
import org.crama.jelin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFriendController {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private UserFriendService userFriendService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/api/user/friends", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public Set<User> getAllUserFriends() {
		User user = userDetailsServiceImpl.getPrincipal();
		Set<User> userFriends = userFriendService.getUserFriends(user);
		return userFriends;
	}
	
	@RequestMapping(value="/api/user/friends/add", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public boolean addFriend(@RequestParam int friend) throws GameException {
		User user = userDetailsServiceImpl.getPrincipal();

		User friendObj = userService.getUser(friend);
		if (friendObj == null) {
			throw new GameException(112, "There is no user with id: " + friend);
		}
		
		return userFriendService.addFriend(user, friendObj);
		
	}
	
	@RequestMapping(value="/api/user/friends/remove", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public boolean removeFriend(@RequestParam int friend) throws GameException {
		User user = userDetailsServiceImpl.getPrincipal();

		User friendObj = userService.getUser(friend);
		if (friendObj == null) {
			throw new GameException(112, "There is no user with id: " + friend);
		}
		
		return userFriendService.removeFriend(user, friendObj);
		
	}
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		System.out.println("User Friend Controller: Game Exception");
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());
		
        return re;
    }
	
	
}
