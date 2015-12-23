package org.crama.jelin.controller;

import java.util.List;

import org.crama.jelin.model.Character;
import org.crama.jelin.model.User;
import org.crama.jelin.service.CharacterService;
import org.crama.jelin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	
	//get all public available character (special = false) 
	@RequestMapping(value="/api/character/first/", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Character> getPublicCharacters() {
		List<Character> characterList = characterService.getPublicCharacters();
        return characterList;
	}
	
	@RequestMapping(value="/api/character/first/", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
    public boolean chooseFirstCharacters(@RequestParam String character) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		        UserDetails userDetails = (UserDetails)auth.getPrincipal();
		        System.out.println(userDetails.getUsername());
		        User user = userService.getUserByUsername(userDetails.getUsername());
		        if (user == null) {
		        	return false;
		        }
		        else {
		        	return characterService.saveUserCharacter(character, user);
		        }
		}
		else {
			return false;
		}
        
	}
	
	@RequestMapping(value="/api/character/current/", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public boolean chooseCurrentCharacters(@RequestParam String character) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		        UserDetails userDetails = (UserDetails)auth.getPrincipal();
		        User user = userService.getUserByUsername(userDetails.getUsername());
		        if (user == null) {
		        	return false;
		        }
		        else {
		        	return characterService.saveCurrentCharacter(character, user);
		        }
		}
		else {
			return false;
		}
        
	}
}
