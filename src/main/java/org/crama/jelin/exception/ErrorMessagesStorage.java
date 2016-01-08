package org.crama.jelin.exception;

public enum ErrorMessagesStorage {
	
	//user errors
	ERROR_101("User is not authenticated"),
	
	//category errors 
	ERROR_201("Category with given id is not found");
	
	//301 - game init errors
	//401 - 
	
	
	private String message;

	private ErrorMessagesStorage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/*private List<RestError> storage;

	public ErrorMessagesStorage(List<RestError> storage) {
		super();
		this.storage = storage;
	}

	public List<RestError> getStorage() {
		return storage;
	}

	public void setStorage(List<RestError> storage) {
		this.storage = storage;
	}*/
	
}
