package org.crama.jelin.service.impl;

import java.util.Date;

import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Constants;
import org.crama.jelin.model.Settings;
import org.crama.jelin.model.SocialUser;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;
import org.crama.jelin.model.UserRole;
import org.crama.jelin.repository.SocialUserRepository;
import org.crama.jelin.repository.UserRepository;
import org.crama.jelin.service.HttpRequestService;
import org.crama.jelin.service.MailService;
import org.crama.jelin.service.SettingsService;
import org.crama.jelin.service.SocialUserService;
import org.crama.jelin.util.EmailValidator;
import org.crama.jelin.util.RandomPasswordGenerator;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("socialUserService")
public class SocialUserServiceImpl implements SocialUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(SocialUserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SocialUserRepository socialUserRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private SettingsService settingsService;
	
	@Autowired
	private HttpRequestService httpRequestService;
	
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
			logger.debug("By email: " + existSocialUser);
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
			logger.debug("By phone: " + existSocialUser);
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
			logger.debug("By provider user id: " + existSocialUser);
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
		existSocialUser.setProviderUserId(socialUser.getProviderUserId());
		logger.debug("Updating social user: " + existSocialUser.getAccessToken() + ", " + existSocialUser.getId());
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
		
		Settings settings = settingsService.getSettings();
		
		mailService.sendRegistrationEmail(user, settings);
		
		return userModel;
	}

	private boolean checkToken(SocialUser socialUser) throws GameException {
		String providerId = socialUser.getProviderId();
		if (providerId.equals("facebook")) {

			String requestURL = Constants.GRAPH_URL  + "me?access_token=" + socialUser.getAccessToken();
			String responseStr = httpRequestService.sendGetRequest(requestURL);
			
			JSONObject jsonResponse = new JSONObject(responseStr);
			
			String facebookUserId = jsonResponse.getString("id");
			logger.debug("Facebook user id: " + facebookUserId);
			if (socialUser.getProviderUserId().equals(facebookUserId)) {
				logger.debug("Facebook user verified!");
				return true;
			}
			
		}
		
		else if (providerId.equals("twitter")) {
			return checkTwitter(socialUser);
			
		}
		else if (providerId.equals("vk")) {
			String requestURL = Constants.VK_URL  + "?access_token=" + socialUser.getAccessToken();
			String responseStr = httpRequestService.sendGetRequest(requestURL);
			logger.debug("VK user: " + responseStr);
			JSONObject jsonResponse = new JSONObject(responseStr);
			
			
			String vkUserId = jsonResponse.getString("id");
			logger.debug("VK user id: " + vkUserId);
			if (socialUser.getProviderUserId().equals(vkUserId)) {
				logger.debug("VK user verified!");
				return true;
			}
			
		}
		else {
			throw new GameException(117, "Wrong providerId. Available providers: facebook, twitter, vk");
		}
		
		
		return false;
	}
	
	private boolean checkTwitter(SocialUser user) throws JSONException, GameException {
		 // Enter your consumer key and secret below
        OAuthService service = new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey(Constants.TWITTER_CLIENT_ID)
                .apiSecret(Constants.TWITTER_CLIENT_SECRET)
                .build();
        
        // Set your access token
        Token accessToken = new Token(user.getAccessToken(), user.getSecret());
        
        OAuthRequest request = new OAuthRequest(Verb.GET, Constants.TWITTER_ACCOUNT_VERIFY_URL);
        request.addHeader("version", "HTTP/1.1");
        request.addHeader("host", Constants.TWITTER_API_HOST);
        request.setConnectionKeepAlive(true);
        
        service.signRequest(accessToken, request);
        
        Response response = request.send();
        
        JSONObject jsonResponse = new JSONObject(response.getBody());
        logger.debug("Response from Twitter: " + jsonResponse.toString());
        
        int responseCode = response.getCode();
        if (responseCode == HttpStatus.OK_200) {
        	
    		String twitterUserId = jsonResponse.getString("id_str");
    		logger.debug("Twitter user id: " + twitterUserId);
    		if (user.getProviderUserId().equals(twitterUserId)) {
    			logger.debug("Twitter user verified!");
    			return true;
    		}
        }
        else {
        	
        	JSONArray errorsArray = jsonResponse.getJSONArray("errors");
        	JSONObject error = (JSONObject)errorsArray.get(0);
        	throw new GameException(error.getInt("code"), error.getString("message"));
    		
        }
        
		return false;
	}
	
}
