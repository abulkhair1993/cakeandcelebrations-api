package com.cakeandcelebration.exception;

import lombok.Data;

@Data
public class CustomError {
	private String title;
	private String errorMessage; 
	
	CustomError(String title,String errorMessage) {
		this.title = title;
		this.errorMessage = errorMessage;
				
	}

}
