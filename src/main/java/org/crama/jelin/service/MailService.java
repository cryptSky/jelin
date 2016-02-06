package org.crama.jelin.service;

import org.crama.jelin.model.UserModel;

public interface MailService {

	void remindPassword(UserModel userModel);

}
