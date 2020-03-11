package com.bridgelabz.fundoonotes.exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class LabelExceptionHandler extends ResponseEntityExceptionHandler 
{
	@ExceptionHandler(LabelNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> allException(LabelNotFoundException labelNotFoundException) 
	{
		ExceptionResponse exceptionResponse = new ExceptionResponse(labelNotFoundException.getMessage(),labelNotFoundException.getHttpstatus());
		return ResponseEntity.status(exceptionResponse.getCode()).body(new ExceptionResponse(exceptionResponse.getMessage(), exceptionResponse.getCode()));
	}

}
