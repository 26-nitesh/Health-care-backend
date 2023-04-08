package com.empservice.employee.utils;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.Map;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
public class Helper {
	private static Encoder encoder = Base64.getEncoder();
	private  static Decoder   decoder = Base64.getDecoder();

	public static String getEncryptedPassword(String password) {
		return encoder.encodeToString(password.getBytes());
	}
	
	public static String decryptPassword(String password) {
		return new String(decoder.decode(password));
	}
	
	public static Object genrateJwtTokken(User user) {
		return Map.of("auth_token",Jwts.builder().setSubject(user.getEmail())
	              .setIssuedAt(new Date(System.currentTimeMillis()))
	              .setExpiration(new Date(System.currentTimeMillis()+40000))
	              .signWith(SignatureAlgorithm.HS256, "DFGDFg".getBytes())
	              .compact());
		
	}
}
