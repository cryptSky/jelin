package org.crama.jelin.model;

public class Constants {
	
	public static final int QUESTION_NUMBER = 3;
	public static final String BUNDLE_ID = "com.nt.Jelin"; 
		
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
		SUMMARY(4);
		
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
	
	public static final String[] NetStatusString = {
			"OFFLINE",
			"SHADOW",
			"ONLINE"
	};
	
	public static final String[] ProcessStatusString = {
			"FREE",
			"CALLING",
			"INVITING",
			"WAITING",
			"INGAME"
	};
	
	public static final String[] GameStateString = {
			"CREATED",
			"IN_PROGRESS",
			"ENDED"
	};
	
	public static final String[] InviteStatusString = {
			"OPEN",
			"ACCEPTED",
			"REJECTED",
			"EXPIRED"
	};
	
	public static final String[] ReadinessString = {
			"CATEGORY",
			"QUESTION",
			"ANSWER",
			"RESULT",
			"SUMMARY"
	};
	
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

