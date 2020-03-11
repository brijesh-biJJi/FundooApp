package com.bridgelabz.fundoonotes.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Data
public class NoteNotFoundException extends RuntimeException {
	private String message;
	private HttpStatus httpstatus;
	public NoteNotFoundException(String message,HttpStatus httpstatus) 
	{
		this.message = message;
		this.httpstatus=httpstatus;
	}
}
