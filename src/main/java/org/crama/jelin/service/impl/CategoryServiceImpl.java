package org.crama.jelin.service.impl;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Category;
import org.crama.jelin.model.Group;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.CategoryRepository;
import org.crama.jelin.service.CategoryService;
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
		List<Category> categories = categoryRepository.getAllCategoriesFromThemes(themeID); 
		
		return categories;
		
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
			throw new GameException(201, "Category with given id doesn't exist");
		}
	}

	@Override
	public Category getCategoryById(int category) {
		return categoryRepository.getCategoryById(category);
	}

	@Override
	public Category getRandomCategoryFromTheme(Category theme) {
		List<Category> categories = getAllCategoriesFromThemes(theme.getId());
		
		Random rand = new Random();
		int index = rand.nextInt(categories.size());
		
		Category category = categories.get(index);
		
		return category;
	}

	@Override
	public Set<Group> getAllAvailableGroups(User user) {
		
		Group publicGroup = categoryRepository.getPulicGroup();
		Set<Group> groupSet = user.getGroupSet();
		groupSet.add(publicGroup);
		return groupSet;
	}



}
