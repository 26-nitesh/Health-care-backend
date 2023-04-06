package com.empservice.employee.utils;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Helper {
	static Encoder encoder = Base64.getEncoder();
	  static Decoder   decoder = Base64.getDecoder();

	public static String getEncryptedPassword(String password) {
		return encoder.encodeToString(password.getBytes());
	}
	
	public static String decryptPassword(String password) {
		return new String(decoder.decode(password));
	}
}
