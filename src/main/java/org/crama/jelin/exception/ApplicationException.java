package org.crama.jelin.exception;

public class ApplicationException extends Exception {
	
	private static final long serialVersionUID = -3628446867058823358L;
	
	private int code;
	private String message;
	
	public ApplicationException(int code, String message) {
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
	@Override
	public String toString() {
		return "ApplicationException [code=" + code + ", message=" + message + "]";
	}
}
