package com.service.organisation.exceptions;

public class CustomExceptions extends Exception{

	private static final long serialVersionUID = 2196459485643738368L;

	public CustomExceptions(String message, Object data) {
		super(String.format("%s", message+" "+data.toString()));
	}
}
