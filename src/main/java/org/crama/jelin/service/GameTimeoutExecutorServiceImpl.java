package org.crama.jelin.service;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;



public class GameTimeoutExecutorServiceImpl implements GameTimeoutExecutorService {

	//private ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
	
	@Override
	public void setUpTimeoutCategory() {
		 /*ScheduledExecutorService executor = Executors.newScheduledThreadPool(2); 
		 final Future handler = executor.submit(new Callable(){ ... });
		 executor.schedule(new Runnable(){
		     public void run(){
		         handler.cancel();
		     }      
		 }, 10000, TimeUnit.MILLISECONDS);*/

	}

	@Override
	public void setUpTimeoutQuestion() {
		/*final CompletableFuture<Response> responseFuture = within(
		        asyncCode(), Duration.ofSeconds(15));
		responseFuture
		        .thenAccept(this::send)
		        .exceptionally(throwable -> {
		            log.error("Unrecoverable error", throwable);
		            return null;
		        });*/

	}

	
	private Object asyncCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUpTimeoutAnswer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUpTimeoutResult() {
		// TODO Auto-generated method stub

	}

}
