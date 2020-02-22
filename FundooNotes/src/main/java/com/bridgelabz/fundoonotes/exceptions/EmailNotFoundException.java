package com.bridgelabz.fundoonotes.exceptions;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
public class EmailNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmailNotFoundException(String message) {
		super(message);
	}
}
