package com.bridgelabz.fundoonotes.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ExceptionResponse {
	String message;
	HttpStatus code;
	public ExceptionResponse(String message, HttpStatus code) {
	this.message = message;
	this.code = code;
	}
	public ExceptionResponse() {}
}
