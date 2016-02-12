package org.crama.jelin.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.crama.jelin.exception.GameException;
import org.crama.jelin.model.Constants;
import org.crama.jelin.model.Settings;
import org.crama.jelin.model.SocialUser;
import org.crama.jelin.model.User;
import org.crama.jelin.model.UserModel;
import org.crama.jelin.model.UserRole;
import org.crama.jelin.repository.SocialUserRepository;
import org.crama.jelin.repository.UserRepository;
import org.crama.jelin.service.MailService;
import org.crama.jelin.service.SettingsService;
import org.crama.jelin.service.SocialUserService;
import org.crama.jelin.util.EmailValidator;
import org.crama.jelin.util.RandomPasswordGenerator;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("socialUserService")
public class SocialUserServiceImpl implements SocialUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SocialUserRepository socialUserRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private SettingsService settingsService;
	
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
		existSocialUser.setProviderUserId(socialUser.getProviderUserId());
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
		
		Settings settings = settingsService.getSettings();
		//TODO send email
		//mailService.sendRegistrationEmail(user, settings);
		
		return userModel;
	}

	private boolean checkToken(SocialUser socialUser) throws GameException {
		String providerId = socialUser.getProviderId();
		if (providerId.equals("facebook")) {

			String requestURL = Constants.GRAPH_URL  + "me?access_token=" + socialUser.getAccessToken();
			String responseStr = sendGetRequest(requestURL);
			JSONObject jsonResponse = new JSONObject(responseStr);
			String facebookUserId = jsonResponse.getString("id");
			System.out.println("Facebook user id: " + facebookUserId);
			if (socialUser.getProviderUserId().equals(facebookUserId)) {
				System.out.println("Facebook user verified!");
				return true;
			}
			
		}
		
		else if (providerId.equals("twitter")) {
			return checkTwitter(socialUser);
			
		}
		else if (providerId.equals("vk")) {
			String requestURL = Constants.VK_URL  + "?access_token=" + socialUser.getAccessToken();
			String responseStr = sendGetRequest(requestURL);
			System.out.println("VK user: " + responseStr);
			JSONObject jsonResponse = new JSONObject(responseStr);
			String vkUserId = jsonResponse.getString("id");
			System.out.println("VK user id: " + vkUserId);
			if (socialUser.getProviderUserId().equals(vkUserId)) {
				System.out.println("VK user verified!");
				return true;
			}
			
		}
		else {
			throw new GameException(117, "Wrong providerId. Available providers: facebook, twitter, vk");
		}
		
		
		return false;
	}
	
	private boolean checkTwitter(SocialUser user) {
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
        System.out.println(jsonResponse);
		String twitterUserId = jsonResponse.getString("id_str");
		System.out.println("Twitter user id: " + twitterUserId);
		if (user.getProviderUserId().equals(twitterUserId)) {
			System.out.println("Twitter user verified!");
			return true;
		}
        
		return false;
	}

	private String sendGetRequest(String requestURL) {
		String result = null;
		HttpClient client = HttpClientBuilder.create().build();	
		try {
			HttpGet getRequest = new HttpGet(requestURL);
			
			HttpResponse response = client.execute(getRequest);
			result = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
