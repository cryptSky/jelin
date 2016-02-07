package org.crama.jelin.service;

import org.crama.jelin.model.Settings;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;

public interface MailService {

	void remindPassword(UserModel userModel, Settings settings);

	void sendRegistrationEmail(User user, Settings settings);

}
