package com.harrison.spring_security_jpa.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {
	private ErrorDetails errorDetails;
	private HttpStatus httpStatus;

	@ExceptionHandler(value = MyException.class)
	public ResponseEntity<Object> handleAllExceptions(MyException exception,WebRequest request){
		this.errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		if (exception.getStatus() != null) {
			 this.httpStatus =  exception.getStatus();
		}
		else {
			this.httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(errorDetails,this.httpStatus);
	}
	
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		this.errorDetails = new ErrorDetails(new Date(),"Validation Failed", exception.getBindingResult().toString());
	    return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	} 
}
