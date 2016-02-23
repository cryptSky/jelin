package org.crama.jelin.controller;

import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.exception.RestError;
import org.crama.jelin.model.Bonus;
import org.crama.jelin.model.Constants.PromocodeStatus;
import org.crama.jelin.model.User;
import org.crama.jelin.service.BonusService;
import org.crama.jelin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BonusController {
	
	private static final Logger logger = LoggerFactory.getLogger(BonusController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BonusService bonusService;
	
	@RequestMapping(value="/api/bonus/checkPromo", method=RequestMethod.GET)
    public @ResponseBody PromocodeStatus checkPromocode(@RequestParam String code) {
		
		User user = userService.getPrincipal();
		
		PromocodeStatus promoStatus = bonusService.checkPromocode(code, user);
		return promoStatus;
		
	}
	
	@RequestMapping(value="/api/bonus/promo", method=RequestMethod.GET)
	
    public @ResponseBody List<Bonus> getPromoBonuses(@RequestParam String code) {
		
		User user = userService.getPrincipal();
		
		List<Bonus> bonuses = bonusService.getBonusesByPromocode(code, user);
		return bonuses;
		
	}
	
	@RequestMapping(value="/api/bonus/register", method=RequestMethod.GET)
    public @ResponseBody List<Bonus> getRegisterBonuses() {
		
		User user = userService.getPrincipal();
		
		List<Bonus> bonuses = bonusService.getEarlyRegisterBonuses(user);
		
		return bonuses;
		
	}
	
	
	@RequestMapping(value="/api/bonus/enter", method=RequestMethod.GET)
    public @ResponseBody List<Bonus> getAppEnterBonuses() {
		
		User user = userService.getPrincipal();
		
		List<Bonus> bonuses = bonusService.getAppEnterBonuses(user);
		
		return bonuses;
		
	}	
	
	@RequestMapping(value="/api/bonus/smminvite", method=RequestMethod.GET)
    public @ResponseBody List<Bonus> getSMMInvitesBonuses() {
		
		User user = userService.getPrincipal();
		
		List<Bonus> bonuses = bonusService.getSMMInvitesBonuses(user);
		
		return bonuses;
		
	}
	
	@RequestMapping(value="/api/bonus/smmshare", method=RequestMethod.GET)
    public @ResponseBody List<Bonus> getSMMSharesBonuses() {
		
		User user = userService.getPrincipal();
		
		List<Bonus> bonuses = bonusService.getSMMSharesBonuses(user);
		
		return bonuses;
		
	}
	
	@RequestMapping(value="/api/bonus/initiated", method=RequestMethod.GET)
    public @ResponseBody List<Bonus> getGameInitiatedBonuses() {
		
		User user = userService.getPrincipal();
		
		List<Bonus> bonuses = bonusService.getGameInitiatedBonuses(user);
		
		return bonuses;
		
	}
	
	@RequestMapping(value="/api/bonus/played", method=RequestMethod.GET)
    public @ResponseBody List<Bonus> getGamePlayedBonuses() {
		
		User user = userService.getPrincipal();
		
		List<Bonus> bonuses = bonusService.getGamePlayedBonuses(user);
		
		return bonuses;
		
	}
	
	
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody RestError handleException(GameException ge) {
		logger.error(ge.getMessage());
		
		RestError re = new RestError(HttpStatus.BAD_REQUEST, ge.getCode(), ge.getMessage());	
        return re;
    }
	
}
