package org.crama.jelin.controller;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Group;
import org.crama.jelin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="/api/question/group", method=RequestMethod.GET)
	@ResponseBody
    public List<Group> getAllGroups() {
		return categoryService.getAllGroups();
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
		System.out.println("Category Controller: Game Exception");

		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());	
        return re;
    }
	
}
