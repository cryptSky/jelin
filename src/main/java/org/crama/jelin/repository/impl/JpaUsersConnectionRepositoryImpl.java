/*package org.crama.jelin.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.crama.jelin.model.SocialUser;
import org.crama.jelin.model.User;
import org.crama.jelin.repository.SocialUserRepository;
import org.crama.jelin.repository.UserRepository;
import org.crama.jelin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;


public class JpaUsersConnectionRepositoryImpl implements UsersConnectionRepository {

	@Autowired
	private SocialUserRepository socialUserRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;

	@Autowired
	private TextEncryptor textEncryptor;

	public JpaUsersConnectionRepositoryImpl(final SocialUserRepository repository, final UserRepository userRepository,
                                        final ConnectionFactoryLocator connectionFactoryLocator,
                                        final TextEncryptor textEncryptor) {
		this.socialUserRepository = repository;
        this.userRepository = userRepository;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
	}

    *//**
     * Find User with the Connection profile (providerId and providerUserId)
     * If this is the first connection attempt there will be nor User so create one and
     * persist the Connection information
     * In reality there will only be one User associated with the Connection
     *
     * @param connection
     * @return List of User Ids (see User.getUuid())
     *//*
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
        List<String> userIds = new ArrayList<String>();
        ConnectionKey key = connection.getKey();
        List<SocialUser> users = socialUserRepository.findByProviderIdAndProviderUserId(key.getProviderId(), key.getProviderUserId());
        if (!users.isEmpty()) {
            for (SocialUser user : users) {
                userIds.add(Integer.toString(user.getUser().getId()));
            }
            return userIds;
        }
        //First time connected so create a User account or find one that is already created with the email address
        User user = findUserFromSocialProfile(connection);
        String userId;
        if(user == null) {
        	//TODO create new user
        	
        	userId = userService.createUser(Role.authenticated).getUserId();
        } else {
           userId = user.getUuid().toString();
        }
        //persist the Connection
        createConnectionRepository(userId).addConnection(connection);
        userIds.add(userId);

        return userIds;
	}

	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		return socialUserRepository.findByProviderIdAndProviderUserId(providerId, providerUserIds);
	}

	public ConnectionRepository createConnectionRepository(String userId) {
		if (userId == null) {
			throw new IllegalArgumentException("userId cannot be null");
		}
        User user = userRepository.findByUuid(userId);
        if(user == null) {
            throw new IllegalArgumentException("User not Found");
        }
		return new JpaConnectionRepository(socialUserRepository, userRepository, user, connectionFactoryLocator, textEncryptor);
	}

    private User findUserFromSocialProfile(Connection connection) {
        User user = null;
        UserProfile profile = connection.fetchUserProfile();
        if(profile != null) {
           user = userRepository.getByEmailAddress(profile.getEmail());
        }
        return user;
    }
	
}
*/