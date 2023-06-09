package com.empservice.employee.utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class APIResponse {

	public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObject) {
		Map<String, Object> map = new HashMap<>();
//		System.out.println(status.value());
		if (message != null && !message.isEmpty())
			map.put("message", message);
		map.put("HttpStatus", status.value());
		if(responseObject!=null && !responseObject.toString().isEmpty())
//			System.out.println(responseObject.toString());
		map.put("data", responseObject);
		map.put("timeStamp", LocalDateTime.now());

		return new ResponseEntity<Object>(map, status);
	}
}
