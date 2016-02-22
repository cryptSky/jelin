package org.crama.jelin.service;

import org.crama.jelin.exception.GameException;

public interface HttpRequestService {
	String sendGetRequest(String requestURL) throws GameException;
}
