package com.bridgelabz.fundoonotes.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */


public class LoginDto {
	private String email;
	private String password;
	
	public LoginDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}

}
