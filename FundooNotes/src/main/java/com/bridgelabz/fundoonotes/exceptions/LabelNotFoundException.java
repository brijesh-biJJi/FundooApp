package com.bridgelabz.fundoonotes.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Data
public class LabelNotFoundException extends RuntimeException{
	private String message;
	private HttpStatus httpstatus;
	public LabelNotFoundException(String message,HttpStatus httpstatus) 
	{
		this.message = message;
		this.httpstatus=httpstatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getHttpstatus() {
		return httpstatus;
	}
	public void setHttpstatus(HttpStatus httpstatus) {
		this.httpstatus = httpstatus;
	}
	
	
}
