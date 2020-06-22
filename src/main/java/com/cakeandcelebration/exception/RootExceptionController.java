package com.cakeandcelebration.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RootExceptionController extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequest(BadRequestException exception, WebRequest request) {
		CustomError cError = new CustomError("Bad Request","Please provide proper input");
		 Map<String, Object> body = new LinkedHashMap<>();
	        body.put("Timestamp", LocalDateTime.now());
	        body.put("Details", cError);
	        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);		
	}
	
	@ExceptionHandler(RegisteredCustomerException.class)
	public ResponseEntity<Object> handleRegCustomerRequest(RegisteredCustomerException exception, WebRequest request) {
		CustomError cError = new CustomError("Internal Server Error","You are already registered customer");
		 Map<String, Object> body = new LinkedHashMap<>();
	        body.put("Timestamp", LocalDateTime.now());
	        body.put("Details", cError);
	        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UnRegisteredCustomerException.class)
	public ResponseEntity<Object> handleRegCustomerRequest(UnRegisteredCustomerException exception, WebRequest request) {
		CustomError cError = new CustomError("Internal Server Error","Please complete registration");
		 Map<String, Object> body = new LinkedHashMap<>();
	        body.put("Timestamp", LocalDateTime.now());
	        body.put("Details", cError);
	        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
