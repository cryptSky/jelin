package org.crama.jelin.service.impl;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.crama.jelin.exception.GameException;
import org.crama.jelin.service.HttpRequestService;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service("httpRequestService")
public class HttpRequestServiceImpl implements HttpRequestService {

	@Override
	public String sendGetRequest(String requestURL) throws GameException {
		String result = null;
		HttpClient client = HttpClientBuilder.create().build();	
		try {
			HttpGet getRequest = new HttpGet(requestURL);
			
			HttpResponse response = client.execute(getRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			
			result = EntityUtils.toString(response.getEntity());
			System.out.println(result);
			
			if (statusCode != HttpStatus.OK_200) {
				throw new GameException(117, "Social service returned error. Check access token");
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
