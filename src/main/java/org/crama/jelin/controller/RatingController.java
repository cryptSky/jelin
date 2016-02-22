package org.crama.jelin.controller;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.User;
import org.crama.jelin.model.json.RatingJson;
import org.crama.jelin.service.RatingService;
import org.crama.jelin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RatingController {
	
	private static final Logger logger = LoggerFactory.getLogger(RatingController.class);
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/api/rating", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<RatingJson> getRating(@RequestParam int time, @RequestParam int people) throws GameException
	{
		User player = userService.getPrincipal();
		List<RatingJson> rating = ratingService.getRating(player, time, people);
		
		return rating;
	}
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		logger.error("Game Controller: Game Exception");
		logger.error(ge.toString());
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());
		return re;
   }

}
