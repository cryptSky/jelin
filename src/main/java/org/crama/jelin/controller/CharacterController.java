package org.crama.jelin.controller;

import java.util.List;
import java.util.Set;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.Character;
import org.crama.jelin.model.Enhancer;
import org.crama.jelin.model.User;
import org.crama.jelin.service.CharacterService;
import org.crama.jelin.service.UserDetailsServiceImpl;
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
public class CharacterController {

	@Autowired
	private CharacterService characterService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	//get all public available character (special = false) 
	@RequestMapping(value="/api/character/first", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Character> getPublicCharacters() {
		List<Character> characterList = characterService.getPublicCharacters();
        return characterList;
	}
	
	@RequestMapping(value="/api/character/first", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
    public boolean chooseFirstCharacter(@RequestParam int character) {
		User user = userDetailsService.getPrincipal();
       
        return characterService.saveUserCharacter(character, user);
        
	}
	
	@RequestMapping(value="/api/character/current", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public boolean chooseCurrentCharacter(@RequestParam int character) {
		User user = userDetailsService.getPrincipal();
		return characterService.saveCurrentCharacter(character, user);
		
	}
	
	//get all characters available for money (special = true, not user character) 
	@RequestMapping(value="/api/character", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Character> getCharactersForSale() {
		User user = userDetailsService.getPrincipal();
		List<Character> characterList = characterService.getCharactersForSale(user);
        return characterList;
	}
	
	@RequestMapping(value="/api/character/enhancer", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Enhancer> getAvailableEnhancers() throws GameException {
		User user = userDetailsService.getPrincipal();
		Set<Character> userCharacters = user.getCharacterSet();
		if (userCharacters == null || userCharacters.size() == 0) {
			throw new GameException(305, "User has no characters. Please choose at least one character first");
		}
		List<Enhancer> enhancerList = characterService.getEnhancerList(user);
		
        return enhancerList;
	}
	
	
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		System.out.println("Character Controller: Game Exception");
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());	
        return re;
    }
	
}
