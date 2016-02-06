package org.crama.jelin.service;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.SocialUser;
import org.crama.jelin.model.UserModel;

public interface SocialUserService {

	UserModel loginSocialUser(SocialUser socialUser) throws GameException;

}
