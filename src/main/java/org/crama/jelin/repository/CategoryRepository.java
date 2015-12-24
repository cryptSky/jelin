package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Group;


public interface CategoryRepository {
	List<Group> getAllGroups();
	
	List<Category> getAllThemesFromGroup(int groupID);
	
	List<Category> getAllCategoriesFromThemes(int themeID);
	
	Category getThemeById(int themeID);
		
}
