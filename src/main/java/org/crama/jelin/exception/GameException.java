package org.crama.jelin.exception;

public class GameException extends Exception {
	
	private static final long serialVersionUID = -3062263086105882477L;
	
	private int code;
	private String message;
	
	public GameException(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}
