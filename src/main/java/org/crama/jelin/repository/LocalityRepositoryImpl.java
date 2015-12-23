package org.crama.jelin.repository;

import java.util.LinkedList;
import java.util.List;

import org.crama.jelin.model.Country;
import org.crama.jelin.model.Locality;
import org.crama.jelin.model.Region;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("localityRepository")
public class LocalityRepositoryImpl implements LocalityRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final String GET_ALL_COUNTRIES = "FROM Country";

	
	private static final String GET_COUNTRY_BY_NAME = "FROM Country "
			+ "WHERE name = :name";
	
	private static final String GET_ALL_REGIONS = "FROM Region";
											
	private static final String GET_REGION_BY_NAME = "FROM Region "
			+ "WHERE name = :name";
	
	private static final String GET_ALL_REGIONS_BY_COUNTRYID = "FROM Region "
			+ "WHERE country_id = :countryId";
	
	private static final String GET_LOCALITY = "FROM Locality "
			+ "WHERE name = :name AND region_id = :regionId";
	
	public List<Country> getAllCountries()
	{
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_COUNTRIES);
		List<Country> countries = new LinkedList<Country>();
		List<Object> objCountries = query.list();;
	    for(final Object country: objCountries){
	    	countries.add((Country)country);
	    }
	    	
		return countries;
	}
	
	public Country getCountryByName(String name)
	{
		Query query = sessionFactory.getCurrentSession().createQuery(GET_COUNTRY_BY_NAME);
		query.setParameter("name", name);
		Country country = (Country)query.uniqueResult();
		return country;
	}
	
	public List<Region> getAllRegions()
	{
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_REGIONS);
		List<Region> regions = new LinkedList<Region>();
		List<Object> objRegions = query.list();;
	    for(final Object region: objRegions){
	    	regions.add((Region)region);
	    }
	    	
		return regions;
	}
	
	
	public Region getRegionByName(String name)
	{
		Query query = sessionFactory.getCurrentSession().createQuery(GET_REGION_BY_NAME);
		query.setParameter("name", name);
		Region region = (Region)query.uniqueResult();
		return region;
	}
	
	public List<Region> getAllRegionsByCountryID(int countryID)
	{
		Query query = sessionFactory.getCurrentSession().createQuery(GET_ALL_REGIONS_BY_COUNTRYID);
		query.setParameter("countryId", countryID);
		List<Region> regions = new LinkedList<Region>();
		List<Object> objRegions = query.list();;
	    for(final Object region: objRegions){
	    	regions.add((Region)region);
	    }
	    	
		return regions;
	}

	@Override
	public Locality getLocality(String name, int region) {
		Query query = sessionFactory.getCurrentSession().createQuery(GET_LOCALITY);
		query.setParameter("name", name);
		query.setParameter("regionId", region);
		Locality locality = (Locality)query.uniqueResult();
		return locality;
	}
	
}
