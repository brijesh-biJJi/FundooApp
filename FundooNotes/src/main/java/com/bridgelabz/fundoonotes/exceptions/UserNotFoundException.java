package com.bridgelabz.fundoonotes.exceptions;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String message) {
		super(message);
	}
}
