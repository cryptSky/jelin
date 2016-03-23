package org.crama.jelin.service.impl;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Constants;
import org.crama.jelin.model.Constants.Language;
import org.crama.jelin.model.Constants.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserSession;
import org.crama.jelin.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.relayrides.pushy.apns.*;
import org.crama.jelin.service.UserSessionService;

import com.relayrides.pushy.apns.util.*;

import io.netty.util.concurrent.Future;

@Service("pushNotificationService")
public class PushNotificationServiceImpl implements PushNotificationService {

	private static final Logger logger = LoggerFactory.getLogger(PushNotificationServiceImpl.class);
	
	private ApnsClient<SimpleApnsPushNotification> apnsClient;
	
	private Future<Void> connectFuture;
		
	@Autowired
	private UserSessionService userSessionService;
	
	public PushNotificationServiceImpl()
	{
		try
		{
			URL resource = this.getClass().getClassLoader().getResource("Jelin_pushes.p12");
			File cert = new File(resource.getFile());
			apnsClient = new ApnsClient<SimpleApnsPushNotification>(cert, Constants.P12_PASSWORD);
			connectFuture = apnsClient.connect(ApnsClient.DEVELOPMENT_APNS_HOST);
			connectFuture.await();
		}
		catch(Exception exception)
		{
			apnsClient = null;
			logger.error("Something wrong happened while initializing service. ");
		}
		
	}
	
	@Override
	public void sendNotificationMessage(User user, NotificationType notificationType, Object... params)
	{
		logger.info("Sending push notification to user: " + user.getUsername() + " ...");
		List<UserSession> sessions = userSessionService.getAllSessions(user);
	    try 
	    {
	    	for (UserSession session: sessions)
	    	{
	    		String message = messageBuilder(session.getLanguage(), notificationType, params);
	    		sendMessage(message, session.getDeviceToken());
	    	}
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	logger.error(e.getMessage());
	    }	   
	}

	private void sendMessage(String message, String deviceToken)
	{
		final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
	    payloadBuilder.setAlertBody(message);
	    final String payload = payloadBuilder.buildWithDefaultMaximumLength();
	    
	    final String token = TokenUtil.sanitizeTokenString(deviceToken);
    	SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token, Constants.BUNDLE_ID, payload);
    	
    	
    	try 
    	{
    		Future<PushNotificationResponse<SimpleApnsPushNotification>> result = apnsClient.sendNotification(pushNotification);
    	    PushNotificationResponse<SimpleApnsPushNotification> pushNotificationReponse =
    	    		result.get();

    	    if (pushNotificationReponse.isAccepted()) {
    	        logger.info("Push notitification accepted by APNs gateway.");
    	    } else {
    	    	logger.info("Notification rejected by the APNs gateway: " +
    	                pushNotificationReponse.getRejectionReason());

    	        if (pushNotificationReponse.getTokenInvalidationTimestamp() != null) {
    	        	logger.info("\t…and the token is invalid as of " +
    	                pushNotificationReponse.getTokenInvalidationTimestamp());
    	        }
    	    }
    	} catch (final ExecutionException e) {
    		logger.error("Failed to send push notification.");
    		logger.error(e.getMessage());

    	    if (e.getCause() instanceof ClientNotConnectedException) {
    	    	logger.info("Waiting for client to reconnect…");
    	       	try {
					apnsClient.getReconnectionFuture().await();
				} catch (Exception e1) {
					logger.error("Failed to reconnect the client..");
					logger.error(e.getMessage());
				}
    	       	logger.info("Reconnected.");
    	    }
    	} catch (InterruptedException e1) {
    		logger.error("Failed to get push notification response..");
		}
    	
	}		
	
	private String messageBuilder(Language language, NotificationType notificationType, Object... params)
	{
		int lIndex = language.getValue();
		int nIndex = notificationType.getValue();
		String result = null; 
		
		String name = null;
		String topic = null;
		int opponentsCount = 0;
		long missedGames = 0;	
		
		switch (notificationType)
		{
			case ACCEPT_RANDOM: name = ((User) params[0]).getUsername();
								topic = ((Category) params[1]).getName();
								result = String.format(Constants.NotificationTypeString[lIndex][nIndex], name, topic);
								break;
								
			case ACCEPT_FRIEND: name = ((User) params[0]).getUsername();
								topic = ((Category) params[1]).getName();
								result = String.format(Constants.NotificationTypeString[lIndex][nIndex], name, topic);
								break;
								
			case ACCEPT_FRIENDS: name = ((User) params[0]).getUsername();
								 topic = ((Category) params[1]).getName();
								 opponentsCount = (int) params[2];
								 result = String.format(Constants.NotificationTypeString[lIndex][nIndex], name, topic, opponentsCount);
								 break;
								 
			case MISSED_GAMES: missedGames = (long) params[0];
			 				   result = String.format(Constants.NotificationTypeString[lIndex][nIndex], missedGames);
			 				   break;
				
		}
		
		return result;
	}

	

}

