package org.crama.jelin.controller;

import org.crama.jelin.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@RequestMapping(value="/api/user/checkFree/", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public boolean checkFree(@RequestParam String username, @RequestParam String email) {
        System.out.println(username + ", " + email);
		return true;
        
    }
	@RequestMapping(value="/api/user/", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody UserModel model) {
        System.out.println(model);
		
    }
}
