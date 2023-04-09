package com.agencyservice.agency.exceptions;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 3012558027226470104L;

//	private String resourceName;
//	private String fieldName;
//	private String fieldValue;
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
		
	}
	
	public ResourceNotFoundException(String resourceName) {
		super(String.format(resourceName));
		
	}
	
	
}

