package org.crama.jelin.service;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Group;
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

}
