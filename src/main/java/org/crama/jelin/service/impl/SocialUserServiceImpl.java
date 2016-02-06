package org.crama.jelin.service.impl;

import java.util.Date;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.SocialUser;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;
import org.crama.jelin.model.UserRole;
import org.crama.jelin.repository.SocialUserRepository;
import org.crama.jelin.repository.UserRepository;
import org.crama.jelin.service.SocialUserService;
import org.crama.jelin.util.EmailValidator;
import org.crama.jelin.util.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("socialUserService")
public class SocialUserServiceImpl implements SocialUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SocialUserRepository socialUserRepository;
	
	@Override
	public UserModel loginSocialUser(SocialUser socialUser) throws GameException {
		
		validateSocialUser(socialUser);
		
		//check if access token is valid
		if (!checkToken(socialUser)) {
			return null;
		}
		//check if user with given email exist
		String providerId = socialUser.getProviderId();
		String email = socialUser.getEmail();
		
		if (email != null) {
			SocialUser existSocialUser = socialUserRepository.getUserByEmailAndProviderId(email, providerId);
			System.out.println("By email: " + existSocialUser);
			if (existSocialUser != null) {
				updateSocialUser(existSocialUser, socialUser);
				
				UserModel userModel = userRepository.getUserModel(existSocialUser.getUser().getUsername());
				return userModel;
			}
		}
		//check if user with given phone number exist
		String phone = socialUser.getPhone();
		if (phone != null) {
			SocialUser existSocialUser = socialUserRepository.getUserByPhoneAndProviderId(phone, providerId);
			System.out.println("By phone: " + existSocialUser);
			if (existSocialUser != null) {
					updateSocialUser(existSocialUser, socialUser);
				
					UserModel userModel = userRepository.getUserModel(existSocialUser.getUser().getUsername());
					return userModel;
			}
		}
		//check if user with given provider user id exist
		String providerUserId = socialUser.getProviderUserId();
		if (providerUserId != null) {
			SocialUser existSocialUser = socialUserRepository.findByProviderIdAndProviderUserId(providerId, providerUserId);
			System.out.println("By provider user id: " + existSocialUser);
			if (existSocialUser != null) {
				updateSocialUser(existSocialUser, socialUser);
				
				UserModel userModel = userRepository.getUserModel(existSocialUser.getUser().getUsername());
				return userModel;
			}
		}
		
		//social user is not created yet
		//check user in user data table
		if (email != null) {
			User user = userRepository.getByEmailAddress(email);
			if (user != null) {
				socialUser.setUser(user);
				socialUserRepository.saveSocialUser(socialUser);
			
				UserModel userModel = userRepository.getUserModel(user.getUsername());
				
				return userModel;
			}
		}
		
		//user not found
		//create new UserModel, User and SocialUser objects
		//generate username, password
		UserModel userModel = generateNewUser(socialUser);
		return userModel;
	}

	private void updateSocialUser(SocialUser existSocialUser, SocialUser socialUser) {
		existSocialUser.setAccessToken(socialUser.getAccessToken());
		System.out.println(existSocialUser.getAccessToken() + ", " + existSocialUser.getId());
		socialUserRepository.update(existSocialUser);
		
	}

	private void validateSocialUser(SocialUser socialUser) throws GameException {
		if (socialUser == null) {
			throw new GameException(117, "Social User is null");
		}
		if (socialUser.getAccessToken() == null) {
			throw new GameException(117, "Social User token is null");
		}
		if (socialUser.getProviderUserId() == null) {
			throw new GameException(117, "Social User provider user id is null");
		}
		if (socialUser.getProviderId() == null) {
			throw new GameException(117, "Social User provider id is null");
		}
		if (socialUser.getFirstName() == null) {
			throw new GameException(117, "Social User first name is null");
		}
		if (socialUser.getEmail() != null) {
			EmailValidator ev = new EmailValidator();
			if (!ev.validate(socialUser.getEmail())) {
				throw new GameException(117, "Social User email is not valid");
			}
		}
		
	}

	private UserModel generateNewUser(SocialUser socialUser) {
		String username = socialUser.getFirstName() + socialUser.getLastName() + socialUser.getProviderUserId();
		String password = RandomPasswordGenerator.getSaltString(10);
		String email = socialUser.getEmail();
		UserModel userModel = new UserModel(username, email, password);
		UserRole role = userRepository.getUserRole(UserRole.ROLE_USER);
		userModel.setRole(role);
		userRepository.saveUserModel(userModel);
		
		User user = new User(socialUser);
		user.setRegisterDate(new Date());
		userRepository.saveUser(user);
		
		socialUser.setUser(user);
		socialUserRepository.saveSocialUser(socialUser);
		
		return userModel;
	}

	private boolean checkToken(SocialUser socialUser) {
		// TODO Auto-generated method stub
		return true;
	}
}
