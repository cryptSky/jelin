package org.crama.jelin.controller;

import java.util.List;
import java.util.Set;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Group;
import org.crama.jelin.model.User;
import org.crama.jelin.service.CategoryService;
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
public class CategoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/api/question/group", method=RequestMethod.GET)
	@ResponseBody
    public Set<Group> getAllGroupsAvailableForUser() {
		User user = userService.getPrincipal();
		return categoryService.getAllAvailableGroups(user);
	}		

	@RequestMapping(value="/api/question/theme", method=RequestMethod.GET)
	@ResponseBody
    public List<Category> getAllThemesFromGroup(@RequestParam int group) {
		List<Category> result = categoryService.getAllThemesFromGroup(group);
		return result;
	}		
	
	@RequestMapping(value="/api/question/category", method=RequestMethod.GET)
	@ResponseBody
    public List<Category> getAllCategoriesFromThemes(@RequestParam int theme) {
		return categoryService.getAllCategoriesFromThemes(theme);
	}		
	
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		logger.error(ge.getMessage());

		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());	
        return re;
    }
	
}
