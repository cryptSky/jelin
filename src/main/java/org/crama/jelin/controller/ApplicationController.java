package org.crama.jelin.controller;

import org.crama.jelin.exception.ApplicationException;
import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.Constants.Language;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserSession;
import org.crama.jelin.service.UserService;
import org.crama.jelin.service.UserSessionService;
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
public class ApplicationController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
	
	@Autowired
	private UserSessionService userSessionService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/api/session", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)

	public void setUpSession(@RequestParam int language, @RequestParam String deviceToken) throws ApplicationException
	{
		User currentUser = userService.getPrincipal();
		if (!Language.isMember(language))
		{
			throw new ApplicationException(100, String.format("Language with id = %d  is not supported by application. "
															+ "Please choose either %s (id = %d) or %s (id = %d) language.", language, Language.ENGLISH.toString(), 
															Language.ENGLISH.getValue(), Language.RUSSIAN.toString(), Language.RUSSIAN.getValue()));
		}
		
		UserSession session = userSessionService.getSession(currentUser);
		
		if (session == null || session.getLanguage().getValue() != language 
				|| !session.getDeviceToken().equals(deviceToken))
		{
			UserSession userSession = new UserSession(currentUser, Language.values()[language], deviceToken);
			
			userSessionService.saveOrUpdate(userSession);
		}
		
	}
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(ApplicationException ge) {
		logger.error(ge.getMessage());
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());	
        return re;
    }
}
