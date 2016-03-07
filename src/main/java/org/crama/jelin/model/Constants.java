package org.crama.jelin.model;

import java.io.File;

public class Constants {
	
	//----settings-------
	
	//public static final String SHORT_GAME_NAME = "Jelin";
	//public static final String SITE_URL = "jelin.ru";
	//public static final String EMAIL = "vitalii.oleksiv@gmail.com";
	
	//public static final LocalDate  EARLY_SIGNUP_START_DATE = LocalDate.of(2016, Month.JANUARY, 1);
	//public static final LocalDate  EARLY_SIGNUP_END_DATE = LocalDate.of(2016, Month.MARCH, 1);
	
	//public static final int QUESTION_NUMBER = 3;
	//----settings-------
	
	public static final String BUNDLE_ID = "com.nt.Jelin"; 
	public static final String P12_PASSWORD = "p124zI78KlmP";
	
	public static final String IMAGE_PATH = "avatar" + File.separator + "users" + File.separator;
	
	//social constants
	public static final String FB_PROVIDER_ID = "facebook";
	public static final String VK_PROVIDER_ID = "vk";
	public static final String TW_PROVIDER_ID = "twitter";
	
	//facebook
	public static final String GRAPH_URL = "https://graph.facebook.com/";
	public static final String FACEBOOK_CLIENT_ID = "1693231397627775";
	public static final String FACEBOOK_CLIENT_SECRET = "14d827d37c58cbb0aebc518bfc26dd8f";
	//twitter
	public static final String TWITTER_ACCOUNT_VERIFY_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
	public static final String TWITTER_API_HOST = "api.twitter.com";
	public static final String TWITTER_CLIENT_ID = "27XxB9wPaP8aK0lo6bHWibiuj";
	public static final String TWITTER_CLIENT_SECRET = "73mdj12K7jihrUlj55Wbtibv2Sl2ENObkSnpro7OAayv2f6zH2";
	//vk
	public static final String VK_URL = "https://api.vk.com/method/users.get";
	public static final String VK_FRIENDS_URL = "https://api.vk.com/method/friends.get?user_id=%s&access_token=%s";


	public enum UserType {
		HUMAN(0),
		BOT(1);
				
		private final int value;
	    private UserType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	public enum NetStatus {
		ONLINE(0),
		SHADOW(1),
		OFFLINE(2);
		
		private final int value;
	    private NetStatus(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	public enum ProcessStatus {
		FREE(0),
		CALLING(1),
		INVITING(2),
		WAITING(3),
		INGAME(4);
		
		private final int value;
	    private ProcessStatus(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	    
	    
	}
	
	public enum GameState {
		CREATED(0),
		IN_PROGRESS(1),
		ENDED(2);
		
		private final int value;
	    private GameState(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	public enum InviteStatus
	{
		OPEN(0),
		ACCEPTED(1),
		REJECTED(2),
		EXPIRED(3);
		
		private final int value;
	    private InviteStatus(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	public enum Readiness
	{
		CATEGORY(0),
		QUESTION(1),
		ANSWER(2),
		RESULT(3),
		SUMMARY(4),
		PENDING(5);
				
		private final int value;
		
	    private Readiness(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	public enum Status {
		
		BOUGHT(0),
		AVAILABLE(1),
		BLOCKED(2);
				
		private final int value;
	    private Status(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	public enum PromocodeStatus {
		
		NOT_FOUND(0),
		ALREADY_USED(1),
		AVAILABLE(2);
				
		private final int value;
	    private PromocodeStatus(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	

	public enum Language {
		ENGLISH(0),
		RUSSIAN(1);
				
		private final int value;
	    private Language(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	    
	    public static boolean isMember(int value)
	    {
	    	boolean result = false;
	    	Language[] languages = Language.values();
	    	for (Language language: languages)
	    	{
	    		if (language.getValue() == value)
	    		{
	    			result = true;
	    			break;
	    		}
	    	}
	    	return result;
	    }
	}
	
	public enum NotificationType {
		
		ACCEPT_RANDOM(0),
		ACCEPT_FRIEND(1),
		ACCEPT_FRIENDS(2),
		MISSED_GAMES(3);
				
		private final int value;
	    private NotificationType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	public static final String[][] NotificationTypeString = {
			{
				"Hi! Jelin user %s has challenged on the topic %s! Are you ready to accept his challenge?",
				"Hi! Your friend %s has challenged you on the topic %s in Jelin quiz! Are you ready to accept his challenge?",
				"Hi! Your friend %s and %d have challenged you on the topic %s in Jelin quiz! Are you ready to accept his challenge?",
				"You’ve got %d invitations from Jelin users and your friends to play quizzes while you were absent. They missed you so much!!"						
			},
			{
				
			}
	};
	
}

