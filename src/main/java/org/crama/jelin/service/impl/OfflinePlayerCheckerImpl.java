package org.crama.jelin.service.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;

import org.crama.jelin.model.Constants;
import org.crama.jelin.model.Constants.Readiness;
import org.crama.jelin.model.Game;
import org.crama.jelin.repository.UserRepository;
import org.crama.jelin.service.OfflinePlayerChecker;
import org.crama.jelin.util.OfflinePlayerWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("offlinePlayerChecker")
public class OfflinePlayerCheckerImpl implements OfflinePlayerChecker {

	private OfflinePlayerWorker offlinePlayerWorker;
	
	@Override
	public ScheduledFuture<?> setUpTimeout(Game game, Readiness condition) {
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		offlinePlayerWorker = new OfflinePlayerWorker(game, condition);
		
	    ScheduledFuture<?> countdown =
	    		scheduler.schedule(offlinePlayerWorker, 
	    				Constants.OFFLINE_TIMEOUT_SEC[condition.getValue()], TimeUnit.SECONDS);  
	
	    while (!countdown.isDone()) {
            try {
                Thread.sleep(1000);
                System.out.println("do other stuff here");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        scheduler.shutdown();
	    return null;
	}

}
