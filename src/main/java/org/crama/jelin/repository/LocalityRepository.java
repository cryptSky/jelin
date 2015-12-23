package org.crama.jelin.repository;

import java.util.List;

import org.crama.jelin.model.Country;
import org.crama.jelin.model.Locality;
import org.crama.jelin.model.Region;

public interface LocalityRepository {
	public List<Country> getAllCountries();
	
	public Country getCountryByName(String name);
	
	public List<Region> getAllRegions();
	
	public Region getRegionByName(String name);
	
	public List<Region> getAllRegionsByCountryID(int countryID);
	
	public Locality getLocality(String name, int regionID);

}
