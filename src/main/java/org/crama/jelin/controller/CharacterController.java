package org.crama.jelin.controller;

import java.util.List;
import java.util.Set;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.Character;
import org.crama.jelin.model.Enhancer;
import org.crama.jelin.model.User;
import org.crama.jelin.service.CharacterService;
import org.crama.jelin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharacterController {

	private static final Logger logger = LoggerFactory.getLogger(CharacterController.class);
	
	@Autowired
	private CharacterService characterService;
	
	@Autowired
	private UserService userService;
	
	//get all public available character (special = false) 
	@RequestMapping(value="/api/character/first", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Character> getPublicCharacters() {
		List<Character> characterList = characterService.getPublicCharacters();
        return characterList;
	}
	
	@RequestMapping(value="/api/character/first", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
    public boolean chooseFirstCharacter(@RequestParam int character) throws GameException {
		
		User user = userService.getPrincipal();
       
        return characterService.saveUserCharacter(character, user);
        
	}
	
	@RequestMapping(value="/api/character/current", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public boolean chooseCurrentCharacter(@RequestParam int character) {
		User user = userService.getPrincipal();
		return characterService.saveCurrentCharacter(character, user);
		
	}
	
	//get current character
	@RequestMapping(value="/api/character/current", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody Character getCurrentCharacter(@RequestParam(required = false) Integer user) throws GameException {
		
		User userObj = null;
		if (user == null) {
			userObj = userService.getPrincipal();
		}
		else {
			userObj = userService.getUser(user);
		}
		
		Character character = characterService.getCurrentCharacter(userObj);
		return character;
		
	}
	
	//get all characters available for money (special = true, not user character) 
	@RequestMapping(value="/api/character", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Character> getCharactersForSale() {
		User user = userService.getPrincipal();
		List<Character> characterList = characterService.getCharactersForSale(user);
        return characterList;
	}
	
	//get all characters user owned 
	@RequestMapping(value="/api/character/users", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody Set<Character> getUserCharacters() {
		User user = userService.getPrincipal();
		Set<Character> characterList = characterService.getUserCharacters(user);
        return characterList;
	}
	
	@RequestMapping(value="/api/character/enhancer", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Enhancer> getAvailableEnhancers() throws GameException {
		User user = userService.getPrincipal();
		Set<Character> userCharacters = user.getCharacterSet();
		if (userCharacters == null || userCharacters.size() == 0) {
			throw new GameException(305, "User has no characters. Please choose at least one character first");
		}
		List<Enhancer> enhancerList = characterService.getEnhancerList(user);
		
        return enhancerList;
	}
	
	@RequestMapping(value="/api/character/buy", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody boolean buyCharacter(@RequestParam int character) throws GameException {
		User user = userService.getPrincipal();
		return characterService.buyCharacter(character, user);
		
	}
	
	@RequestMapping(value="/api/character/enhancer/buy", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody boolean buyEnhancer(@RequestParam int enhancer) throws GameException {
		User user = userService.getPrincipal();
		return characterService.buyEnhancer(enhancer, user);
		
	}
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		logger.error(ge.getMessage());
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());	
        return re;
    }
	
}
