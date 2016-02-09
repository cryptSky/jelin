package org.crama.jelin.service.impl;

import org.crama.jelin.model.Settings;
import org.crama.jelin.repository.SettingsRepository;
import org.crama.jelin.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("settingsService")
public class SettingsServiceImpl implements SettingsService {

	@Autowired
	private SettingsRepository settingsRepository;
	
	@Override
	public Settings getSettings() {
		
		return settingsRepository.getSettings();
		
	}

}
