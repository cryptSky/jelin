package org.crama.jelin.repository;

import org.crama.jelin.model.SocialUser;

public interface SocialUserRepository {

	SocialUser findByProviderIdAndProviderUserId(String providerId, String providerUserId);

	SocialUser getUserByPhoneAndProviderId(String phone, String providerId);

	SocialUser getUserByEmailAndProviderId(String email, String providerId);

	void saveSocialUser(SocialUser socialUser);

	void update(SocialUser existSocialUser);

}
