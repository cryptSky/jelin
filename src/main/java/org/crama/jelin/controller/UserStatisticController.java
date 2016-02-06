package org.crama.jelin.controller;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserStatistics;
import org.crama.jelin.model.json.RatingJson;
import org.crama.jelin.service.UserService;
import org.crama.jelin.service.UserStatisticsService;
import org.crama.jelin.service.impl.UserDetailsServiceImpl;
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
public class UserStatisticController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserStatisticsService userStatisticsService;
	
	@RequestMapping(value="/api/user/stats", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public UserStatistics getUserStats(@RequestParam(required = false) Integer user) {
		
		User userObj = null;
		if (user == null) {
			userObj = userService.getPrincipal();
		}
		else {
			userObj = userService.getUser(user);
		}
		
		return userObj.getUserStatistics();
	}
	
	
	//TODO add to API
	@RequestMapping(value="/api/user/acorns/buy", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public void buyGoldAcorns(@RequestParam Integer acorns) {
		
		User user = userService.getPrincipal();
		userStatisticsService.buyGoldAcorns(user, acorns);
		
	}
	
	//TODO add to API 
	@RequestMapping(value="/api/user/social/invite", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public void socialInvite() {
		User user = userService.getPrincipal();
		userStatisticsService.increaseSocialInvites(user);
		
	}
	
	//TODO add to API 
	@RequestMapping(value="/api/user/social/share", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public void socialShare() {
		User user = userService.getPrincipal();
		userStatisticsService.increaseSocialShares(user);
	}
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		System.out.println("User Statistics Controller: Game Exception");
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());
		
        return re;
    }
	
}
