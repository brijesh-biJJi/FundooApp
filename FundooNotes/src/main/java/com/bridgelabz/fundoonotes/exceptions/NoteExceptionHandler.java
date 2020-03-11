package com.bridgelabz.fundoonotes.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NoteExceptionHandler extends ResponseEntityExceptionHandler 
{
	@ExceptionHandler(NoteNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> allException(NoteNotFoundException noteNotFoundException) 
	{
		ExceptionResponse exceptionResponse = new ExceptionResponse(noteNotFoundException.getMessage(),noteNotFoundException.getHttpstatus());
		return ResponseEntity.status(exceptionResponse.getCode()).body(new ExceptionResponse(exceptionResponse.getMessage(), exceptionResponse.getCode()));
	}

}
