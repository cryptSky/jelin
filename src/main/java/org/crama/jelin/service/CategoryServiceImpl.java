package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.exception.ErrorMessagesStorage;
import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Group;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Group> getAllGroups() {
		return categoryRepository.getAllGroups();
	}

	@Override
	public List<Category> getAllThemesFromGroup(int groupID) {
		return categoryRepository.getAllThemesFromGroup(groupID);
	}

	@Override
	public List<Category> getAllCategoriesFromThemes(int themeID) {
		return categoryRepository.getAllCategoriesFromThemes(themeID);
	}

	@Override
	public Category getThemeById(int themeID) {
		return categoryRepository.getThemeById(themeID);
	}

	@Override
	public List<Category> getChildThemesByParent(Category parent) {
		return categoryRepository.getChildThemesByParentId(parent.getId());
	}

	@Override
	public void checkCategoryNotNull(Category category) throws GameException {
		if (category== null) {
			throw new GameException(201, ErrorMessagesStorage.ERROR_201.getMessage());
		}
	}

	@Override
	public Category getCategoryById(int category) {
		return categoryRepository.getCategoryById(category);
	}



}
