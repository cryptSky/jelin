package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Country;
import org.crama.jelin.model.Locality;
import org.crama.jelin.model.Region;

public interface LocalityRepository {
	List<Country> getAllCountries();
	
	Country getCountryByName(String name);
	
	List<Region> getAllRegions();
	
	Region getRegionByName(String name);
	
	List<Region> getAllRegionsByCountryID(int country);
	
	Locality getLocality(String name, int region);

}
