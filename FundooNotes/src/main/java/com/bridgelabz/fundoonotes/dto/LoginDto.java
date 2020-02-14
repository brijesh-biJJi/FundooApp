package com.bridgelabz.fundoonotes.dto;

public class LoginDto {
	private String email;
	private String password;
	
	
	public String getPassword() {
		return password;
	}
	
	public LoginDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

}
