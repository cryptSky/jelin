package org.crama.jelin.service;

import java.util.List;
import java.util.Set;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Group;
import org.crama.jelin.model.User;

public interface CategoryService {
	List<Group> getAllGroups();
	
	List<Category> getAllThemesFromGroup(int groupID);
	
	List<Category> getAllCategoriesFromThemes(int themeID);
	
	Category getThemeById(int themeID);
	
	List<Category> getChildThemesByParent(Category parent);

	void checkCategoryNotNull(Category category) throws GameException;

	Category getCategoryById(int category);
	
	Category getRandomCategoryFromTheme(Category theme);

	Set<Group> getAllAvailableGroups(User user);
}
