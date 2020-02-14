package com.bridgelabz.fundoonotes.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
/**
 * 
 * @author Brijesh A Kanchan
 *
 */

@Data
@Component
public class RegisterDto {

	private String name;
	private String email;
	private String password;
	private long phone;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	
	
}
