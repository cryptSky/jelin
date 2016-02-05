package org.crama.jelin.util;

import java.util.Random;

public class RandomPasswordGenerator {
	
	private static Random rand = new Random();
	
	public static String getSaltString(int passLength) {
		if (passLength < 0) {
			return null;
		}
        String SALTCHARS = "abcdefghijklmnoprstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        
        while (salt.length() < passLength) {
            int index = (int) (rand.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
