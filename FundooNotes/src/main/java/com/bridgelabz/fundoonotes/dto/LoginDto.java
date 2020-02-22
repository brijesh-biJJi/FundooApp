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
}
