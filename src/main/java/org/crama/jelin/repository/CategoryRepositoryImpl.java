package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Group;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("categoryRepository")
public class CategoryRepositoryImpl implements CategoryRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final String GET_ALL_GROUPS = "FROM Group";

	private static final String GET_THEME_BY_ID = "FROM Category "
			+ "WHERE is_category = FALSE AND id = :themeId";
	
	private static final String GET_CATEGORY_BY_ID = "FROM Category "
			+ "WHERE is_category = TRUE AND id = :categoryId";
	
	private static final String GET_ALL_THEMES_FROM_GROUP = "FROM Category "
			+ "WHERE is_category = FALSE AND group_id = :groupId";
											
	private static final String GET_ALL_CATEGORIES_FROM_THEMES = "FROM Category "
			+ "WHERE is_category = TRUE AND parent_id = :themeId";
	
	private static final String GET_CHILD_THEMES_BY_PARENT_ID = "FROM Category "
			+ "WHERE is_category = FALSE AND parent_id = :themeId";
	
	@Override
	public List<Group> getAllGroups() {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_GROUPS);
		
		@SuppressWarnings("unchecked")
		List<Group> groups = query.list();
	    	
		return groups;
	}

	@Override
	public List<Category> getAllThemesFromGroup(int groupID) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_THEMES_FROM_GROUP);
		query.setParameter("groupId", groupID);
		@SuppressWarnings("unchecked")
		List<Category> themes = query.list();
	    	
		return themes;
	}

	@Override
	public List<Category> getAllCategoriesFromThemes(int themeID) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_CATEGORIES_FROM_THEMES);
		query.setParameter("themeId", themeID);
		
		@SuppressWarnings("unchecked")
		List<Category> themes = query.list();
	    	
		return themes;
	}

	@Override
	public Category getThemeById(int themeID) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_THEME_BY_ID);
		query.setParameter("themeId", themeID);
		Category theme = (Category)query.uniqueResult();
	    	
		return theme;
	}
	
	@Override
	public Category getCategoryById(int categoryID) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_CATEGORY_BY_ID);
		query.setParameter("categoryId", categoryID);
		Category category = (Category)query.uniqueResult();
	    	
		return category;
	}


	@Override
	public List<Category> getChildThemesByParentId(int parentID) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_CHILD_THEMES_BY_PARENT_ID);
		query.setParameter("themeId", parentID);
		
		@SuppressWarnings("unchecked")
		List<Category> themes = query.list();
	    	
		return themes;
	}

}
