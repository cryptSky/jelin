package org.crama.jelin.service;

import org.crama.jelin.model.User;
import org.crama.jelin.model.Constants.NotificationType;

public interface PushNotificationService {
	
	void sendNotificationMessage(User user, NotificationType notificationType, Object... params);
	
}

 