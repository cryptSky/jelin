package org.crama.jelin.repository.impl;

import org.crama.jelin.model.Settings;
import org.crama.jelin.repository.SettingsRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("settingsRepository")
public class SettingsRepositoryImpl implements SettingsRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Settings getSettings() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Settings.class);
		Settings settings = (Settings)criteria.uniqueResult();
		return settings;
	}

}
