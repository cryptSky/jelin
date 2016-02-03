package org.crama.jelin.service;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLException;

import org.crama.jelin.model.Category;
import org.crama.jelin.model.Constants;
import org.crama.jelin.model.Constants.Language;
import org.crama.jelin.model.Constants.NotificationType;
import org.crama.jelin.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserSession;
import org.crama.jelin.repository.GameInitRepository;
import org.crama.jelin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.relayrides.pushy.apns.*;

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
		}
		
	}
	
	@Override
	public void sendNotificationMessage(User user, NotificationType notificationType, Object... params)
	{
		List<UserSession> sessions = userSessionService.getAllSessions(user);
	    
	    for (UserSession session: sessions)
	    {
	    	String message = messageBuilder(session.getLanguage(), notificationType, params);
	    	sendMessage(message, session.getDeviceToken());
	    }
	   
	}

	/*
	@Override
	public void sendPushInviteRandom(User user, User initiator, Category theme) {
		UserSession session = userSessionService.getSession(user);
		Language language = session.getLanguage();
		
		int lIndex = language.getValue();
		int nIndex = NotificationType.ACCEPT_RANDOM.getValue();
		String name = initiator.getUsername();
		String topic = theme.getName();
		String message = String.format(Constants.NotificationTypeString[lIndex][nIndex], name, topic);
		
		sendNotificationMessage(user, message);		
	}
	
	@Override
	public void sendPushInviteFriend(User user, User initiator, Category theme) {
		UserSession session = userSessionService.getSession(user);
		Language language = session.getLanguage();
		
		int lIndex = language.getValue();
		int nIndex = NotificationType.ACCEPT_FRIEND.getValue();
		String name = initiator.getUsername();
		String topic = theme.getName();
		String message = String.format(Constants.NotificationTypeString[lIndex][nIndex], name, topic);
		
		sendNotificationMessage(user, message);	
		
	}

	@Override
	public void sendPushInviteFriends(User user, User initiator, Category theme, int opponentsCount) {
		UserSession session = userSessionService.getSession(user);
		Language language = session.getLanguage();
		
		int lIndex = language.getValue();
		int nIndex = NotificationType.ACCEPT_FRIENDS.getValue();
		String name = initiator.getUsername();
		String topic = theme.getName();
		String message = String.format(Constants.NotificationTypeString[lIndex][nIndex], name, topic, opponentsCount);
		
		sendNotificationMessage(user, message);	
		
	}

	@Override
	public void sendPushMissedGames(User user) {
		UserSession session = userSessionService.getSession(user);
		Language language = session.getLanguage();
		
		int lIndex = language.getValue();
		int nIndex = NotificationType.MISSED_GAMES.getValue();
		
		int missedGames = gameInitRepository.getExpiredInvites(user);
		
		String message = String.format(Constants.NotificationTypeString[lIndex][nIndex], missedGames);
		
		sendNotificationMessage(user, message);	
		
	} */
	
	private void sendMessage(String message, String deviceToken)
	{
		final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
	    payloadBuilder.setAlertBody(message);
	    final String payload = payloadBuilder.buildWithDefaultMaximumLength();
	    
	    final String token = TokenUtil.sanitizeTokenString(deviceToken);
    	SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token, Constants.BUNDLE_ID, payload);
    	Future<PushNotificationResponse<SimpleApnsPushNotification>> result = apnsClient.sendNotification(pushNotification);
    	
    	try 
    	{
    	    PushNotificationResponse<SimpleApnsPushNotification> pushNotificationReponse =
    	    		result.get();

    	    if (pushNotificationReponse.isAccepted()) {
    	        logger.debug("Push notitification accepted by APNs gateway.");
    	    } else {
    	    	logger.debug("Notification rejected by the APNs gateway: " +
    	                pushNotificationReponse.getRejectionReason());

    	        if (pushNotificationReponse.getTokenInvalidationTimestamp() != null) {
    	        	logger.debug("\t…and the token is invalid as of " +
    	                pushNotificationReponse.getTokenInvalidationTimestamp());
    	        }
    	    }
    	} catch (final ExecutionException e) {
    		logger.error("Failed to send push notification.");
    	    e.printStackTrace();

    	    if (e.getCause() instanceof ClientNotConnectedException) {
    	    	logger.debug("Waiting for client to reconnect…");
    	       	try {
					apnsClient.getReconnectionFuture().await();
				} catch (InterruptedException e1) {
					logger.error("Failed to reconnect the client..");
				}
    	       	logger.debug("Reconnected.");
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
		int missedGames = 0;	
		
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
								 
			case MISSED_GAMES: missedGames = (int) params[0];
			 				   result = String.format(Constants.NotificationTypeString[lIndex][nIndex], missedGames);
			 				   break;
				
		}
		
		return result;
	}

	

}
