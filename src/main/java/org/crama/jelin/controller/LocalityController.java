package org.crama.jelin.controller;

import java.util.List;


import org.crama.jelin.model.Country;
import org.crama.jelin.model.Region;
import org.crama.jelin.model.Locality;
import org.crama.jelin.service.LocalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/location")
public class LocalityController {
	
	@Autowired
	private LocalityService localityService;
	
	@RequestMapping(value="/country", method=RequestMethod.GET)
	@ResponseBody
    public List<Country> getAllCountries() {
		return localityService.getAllCountries();
	}		

	@RequestMapping(value="/country", method=RequestMethod.GET, params={"name"})
	@ResponseBody
    public Country getCountry(@RequestParam(required = false) String name) {
		return localityService.getCountryByName(name);
	}		
	
	@RequestMapping(value="/region", method=RequestMethod.GET)
	@ResponseBody
    public List<Region> getAllregions() {
		return localityService.getAllRegions();
	}		
	
	@RequestMapping(value="/region", method=RequestMethod.GET, params={"name"})
	@ResponseBody
    public Region getRegionByName(@RequestParam String name) {
		return localityService.getRegionByName(name);
	}	
	
	@RequestMapping(value="/region", method=RequestMethod.GET, params={"country"})
	@ResponseBody
    public List<Region> getAllregions(@RequestParam Integer country) {
		return localityService.getAllRegionsByCountryID(country);
	}
	
	@RequestMapping(value="/locality", method=RequestMethod.GET)
	@ResponseBody
    public Locality getLocality(@RequestParam String name, @RequestParam Integer region) {
		return localityService.getLocality(name, region);
	}	
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody String handleException(MethodArgumentNotValidException exception) {
		System.out.println("Validation Exception");
        return exception.getMessage();
    }
}
