package com.service.hospital.utils;


public class User {

private String email;
private String password;
private String newPassword;


public String getNewPassword() {
	return newPassword;
}



public void setNewPassword(String newPassword) {
	this.newPassword = newPassword;
}



public User() {
	// TODO Auto-generated constructor stub
}



public String getEmail() {
	return email;
}



public void setEmail(String email) {
	this.email = email;
}



public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

}
