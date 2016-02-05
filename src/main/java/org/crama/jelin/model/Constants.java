package org.crama.jelin.model;

import java.time.LocalDate;
import java.time.Month;

public class Constants {
	
	public static final String SHORT_GAME_NAME = "Jelin";
	public static final String SITE_URL = "jelin.ru";
	public static final String EMAIL = "vitalii.oleksiv@gmail.com";
	
	public static final LocalDate  EARLY_SIGNUP_START_DATE = LocalDate.of(2016, Month.JANUARY, 1);
	public static final LocalDate  EARLY_SIGNUP_END_DATE = LocalDate.of(2016, Month.MARCH, 1);
	
	public static final int QUESTION_NUMBER = 3;
	
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
}

