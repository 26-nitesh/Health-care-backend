package com.service.organisation.util;

import java.security.Key;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
public class Helper {
	private  static Decoder   decoder = Base64.getDecoder();

	public static String getEncryptedPassword(String password) {
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(password.getBytes());
	}
	
	public static String decryptPassword(String password) {
		return new String(decoder.decode(password));
	}
//	public static Key secretsKey() {
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(DatatypeConverter.printBase64Binary("MyOwnSecretKey".getBytes()));
//       System.out.println(new SecretKeySpec(apiKeySecretBytes, "HmacSHA256"));
//        return new SecretKeySpec(apiKeySecretBytes, "HmacSHA256");
//    }
	public static Object genrateJwtTokken(User user) {
//		System.out.println(key);
		return Map.of("auth_token",Jwts.builder().setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+40000))
                .signWith(SignatureAlgorithm.HS256, "sssadc".getBytes())
                .compact());
	}
}
