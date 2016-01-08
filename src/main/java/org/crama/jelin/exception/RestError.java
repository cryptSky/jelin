package org.crama.jelin.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class RestError implements Serializable {
	
	private static final long serialVersionUID = -489760647097791933L;
	
	private HttpStatus status;
	private int code;
	private String message;
	//private String developerMessage;
	//private String modeInfo;
	
	public RestError(HttpStatus status, int code, String message) {
		
		this.status = status;
		this.code = code;
		this.message = message;
		
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public void setStatus(HttpStatus status) {
		this.status = status;
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
	/*public String getDeveloperMessage() {
		return developerMessage;
	}
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}*/
	
	

}
