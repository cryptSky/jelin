package org.crama.jelin.service.impl;

import java.util.List;

import org.crama.jelin.model.Country;
import org.crama.jelin.model.Locality;
import org.crama.jelin.model.Region;
import org.crama.jelin.repository.LocalityRepository;
import org.crama.jelin.service.LocalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("localityService")
public class LocalityServiceImpl implements LocalityService {

	@Autowired
	private LocalityRepository localityRepository;
	
	@Override
	public List<Country> getAllCountries() {
		return localityRepository.getAllCountries();
	}

	@Override
	public Country getCountryByName(String name) {
		return localityRepository.getCountryByName(name);
	}

	@Override
	public List<Region> getAllRegions() {
		return localityRepository.getAllRegions();
	}

	@Override
	public Region getRegionByName(String name) {
		return localityRepository.getRegionByName(name);
	}

	@Override
	public List<Region> getAllRegionsByCountryID(int countryID) {
		return localityRepository.getAllRegionsByCountryID(countryID);
	}

	@Override
	public Locality getLocality(String name, int region) {
		return localityRepository.getLocality(name, region);
	}

}
