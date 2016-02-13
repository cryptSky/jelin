package org.crama.jelin.service;

import org.crama.jelin.model.Constants.Readiness;
import org.crama.jelin.model.Game;

public interface OfflinePlayerChecker {
	void setUpTimeout(Game game, Readiness condition);
}
