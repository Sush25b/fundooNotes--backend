package com.bridgelabz.fundooNotes.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
	@ExceptionHandler(UserException.class)
	public ResponseEntity<UserErrorResponse> handleUserException(UserException exc)
	{
		UserErrorResponse error=new UserErrorResponse();
		
		error.setMessage(exc.getMessage()); //get Message from parent class-->RuntimeException
		error.setStatus(exc.errorCode); 
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<>(error,HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<UserErrorResponse> handleException(Exception exc)
	{
		UserErrorResponse error=new UserErrorResponse();
		
		error.setMessage(exc.getMessage());
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());	//INTERNAL_SERVER_ERROR =500
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);  //INTERNAL_SERVER_ERROR =500
	}
	
	
}
