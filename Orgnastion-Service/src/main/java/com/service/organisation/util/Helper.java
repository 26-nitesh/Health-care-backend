package com.service.organisation.util;

import java.util.Base64;
import java.util.Base64.Encoder;

public class Helper {

	public static String getEncryptedPassword(String password) {
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(password.getBytes());
	}
}
