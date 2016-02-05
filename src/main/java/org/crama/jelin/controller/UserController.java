package org.crama.jelin.controller;

import java.util.List;

import javax.validation.Valid;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.Constants.NetStatus;
import org.crama.jelin.model.SocialUser;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;
import org.crama.jelin.service.SocialUserService;
import org.crama.jelin.service.UserActivityService;
import org.crama.jelin.service.UserService;
import org.crama.jelin.service.UserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private SocialUserService socialUserService;

	@Autowired
	private UserStatisticsService userStatisticsService;
	
	@RequestMapping(value="/api/user/checkFree", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public boolean checkFree(@RequestParam(required = false) String username, @RequestParam(required = false) String email) {
		System.out.println(username + ", " + email);
		if (username != null && email != null) {
			
			boolean isFreeUsername = userService.checkUsername(username);
	        
	        boolean isFreeEmail = userService.checkEmail(email);
	        return isFreeUsername && isFreeEmail;
		}
		else if (username != null) {
			boolean isFreeUsername = userService.checkUsername(username);
	        return isFreeUsername;
		}
		else if (email != null) {
	     
	        boolean isFreeEmail = userService.checkEmail(email);
	        return isFreeEmail;
		}
		else {
			return false;
		}
	}		

	
	@RequestMapping(value="/api/user", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
    public boolean signup(@Valid @RequestBody UserModel model, BindingResult result) {
		System.out.println(model);
		if (result.hasErrors()) {
			
			System.out.println("Validation failed");
            return false;
        }
       
		return userService.saveUser(model);
        
    }
	
	
	@RequestMapping(value="/api/user/login", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public boolean checkUserCredentials() throws GameException {
		User user = userService.getPrincipal();
		
		if (user == null) { 
			throw new GameException(101, "User is not authenticated");
		}
		userActivityService.saveUserLoginActivity(user);
		userStatisticsService.updateDaysInGame(user);
        return true;
	}
	
	@RequestMapping(value="/api/user", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody User getUserPrincipal() {
		User user = userService.getPrincipal();
        return user;
    }
	
	@RequestMapping(value="/api/user/all", method=RequestMethod.GET)
	public @ResponseBody List<User> getAllUsers() {
		User user = userService.getPrincipal();
		return userService.getAllUsers(user);
	}
	
	@RequestMapping(value="/api/user/netstatus", method=RequestMethod.POST)
	public void changeNetStatus(@RequestParam int status) throws GameException {
		
		User user = userService.getPrincipal();
		userService.changeNetStatus(user, status);
		if (status == 2) {
			userActivityService.saveUserOnlineActivity(user);
		}
	}
	
	@RequestMapping(value="/api/user/recovery", method=RequestMethod.POST)
	public void recoverPassword(@RequestParam String access) throws GameException {
		
		userService.remindPassword(access);
		
	}
	
	
	
    @RequestMapping(value="/api/user/login/social", method=RequestMethod.PUT)
    public @ResponseBody UserModel socialLogin(@RequestBody SocialUser socialUser) 
    		throws GameException {
		UserModel userModel = socialUserService.loginSocialUser(socialUser);
		if (userModel != null) {
			User user = userService.getUserByUsername(userModel.getUsername());
			userActivityService.saveUserLoginActivity(user);
		}
		return userModel;
		
		/*OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.
				getConnectionFactory(providerId);
	    Connection<?> connection = connectionFactory.createConnection(new AccessGrant(request.getAccessToken()));
	    SocialUser socialUser = userService.socialLogin(connection);
	    return socialUser;*/
	}
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		System.out.println("User Controller: Game Exception");
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());
		
        return re;
    }
	
	//DEVELOPMENT METHODS
	
	//only for testing
	@RequestMapping(value="/api/user/netstatus/all", method=RequestMethod.POST)
	public void changeUsersNetStatus(@RequestParam int status) throws GameException {
		
		User user = userService.getPrincipal();
		userService.changeOthersNetStatus(user, status);
		
	}
	
	@RequestMapping(value="/api/user/infostats", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public void createUsersInfoAndStatistics() {
		
		userService.createUsersInfoAndStatistics();
		
		
	}
	
	
}

