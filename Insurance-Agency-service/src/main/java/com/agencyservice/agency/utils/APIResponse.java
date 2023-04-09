package com.agencyservice.agency.utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class APIResponse {

	public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObject) {
		Map<String, Object> map = new HashMap<>();

		if (message != null && !message.isEmpty())
			map.put("message", message);
		map.put("HttpStatus", status.value());
//		System.out.println(responseObject);
		if(responseObject!=null && !responseObject.toString().isEmpty())
		map.put("data", responseObject);
		map.put("timeStamp", LocalDateTime.now().toString());
		return new ResponseEntity<Object>(map, status);
	}
}
