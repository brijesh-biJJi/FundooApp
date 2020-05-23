package com.bridgelabz.fundoonotes.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */

@Data
public class LoginDto {
	private String email;
	private String password;
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
