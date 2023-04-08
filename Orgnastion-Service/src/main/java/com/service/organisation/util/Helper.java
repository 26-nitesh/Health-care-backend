package com.service.organisation.util;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Helper {
	private  static Decoder   decoder = Base64.getDecoder();

	 
	public static String getEncryptedPassword(String password) {
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(password.getBytes());
	}
	
	public static String decryptPassword(String password) {
		return new String(decoder.decode(password));
	}
}
