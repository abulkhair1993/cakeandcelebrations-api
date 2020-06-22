package com.cakeandcelebration.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SignUp implements Serializable {
	private String name;

	private String mobile;

	private String secretKey;
	
	public SignUp() {}

}
