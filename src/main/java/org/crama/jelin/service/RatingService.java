package org.crama.jelin.service;
import java.util.List;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.User;
import org.crama.jelin.model.json.RatingJson;

public interface RatingService {
	List<RatingJson> getRating(User player, int time, int people) throws GameException;
}
