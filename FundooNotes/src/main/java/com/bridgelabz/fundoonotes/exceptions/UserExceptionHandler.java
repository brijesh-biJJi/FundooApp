package com.bridgelabz.fundoonotes.exceptions;




import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler 
{
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> allException(UserNotFoundException userNotFoundException) 
	{
		ExceptionResponse exceptionResponse = new ExceptionResponse(userNotFoundException.getMessage(),userNotFoundException.getHttpstatus());
		return ResponseEntity.status(exceptionResponse.getCode()).body(new ExceptionResponse(exceptionResponse.getMessage(), exceptionResponse.getCode()));
	}

}