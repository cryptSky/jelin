package org.crama.jelin.repository;

import org.crama.jelin.model.SocialUser;
import org.crama.jelin.model.User;

public interface SocialUserRepository {

	SocialUser findByProviderIdAndProviderUserId(String providerId, String providerUserId);

	SocialUser getUserByPhoneAndProviderId(String phone, String providerId);

	SocialUser getUserByEmailAndProviderId(String email, String providerId);
	
	SocialUser getSocialUser(User user);

	void saveSocialUser(SocialUser socialUser);

	void update(SocialUser existSocialUser);

}
