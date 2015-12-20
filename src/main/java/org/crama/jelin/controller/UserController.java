package org.crama.jelin.controller;

import javax.validation.Valid;

import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;
import org.crama.jelin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	
	@RequestMapping(value="/api/user/checkFree", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public boolean checkFree(@RequestParam String username, @RequestParam String email) {
        System.out.println(username);
        boolean isFreeUsername = userService.checkUsername(username);
        
        boolean isFreeEmail = userService.checkEmail(email);
        return isFreeUsername && isFreeEmail;
        
        
    }
	@RequestMapping(value="/api/user/", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
    public boolean signup(@Valid @RequestBody UserModel model, BindingResult result) {
		System.out.println(model);
		if (result.hasErrors()) {
			System.out.println("Validation failed");
            return false;
        }
       
		return userService.saveUser(model);
        
    }
	
	@RequestMapping(value="/api/user/login/", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public boolean checkUserCredentials() {
        return true;
	}
	
	//TODO add functionality
	@RequestMapping(value="/api/user/", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public @ResponseBody User getUserPrincipal() {
        return new User(1, "user", "user@gmail.com");
    }
	
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody String handleException(MethodArgumentNotValidException exception) {
		System.out.println("Validation Exception");
        return exception.getMessage();
    }
	
}

