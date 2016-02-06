package org.crama.jelin.service;

import org.crama.jelin.model.Constants.Readiness;
import org.crama.jelin.model.Game;

import java.util.concurrent.ScheduledFuture;

public interface OfflinePlayerChecker {
	ScheduledFuture<?> setUpTimeout(Game game, Readiness condition);
}
