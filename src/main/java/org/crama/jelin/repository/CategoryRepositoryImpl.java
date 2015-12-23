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

	
	private static final String GET_ALL_THEMES_FROM_GROUP = "FROM Category "
			+ "WHERE is_category = FALSE AND group_id = :groupId";
											
	private static final String GET_ALL_CATEGORIES_FROM_THEMES = "FROM Category "
			+ "WHERE is_category = TRUE AND parent_id = :themeId";
	
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

}
