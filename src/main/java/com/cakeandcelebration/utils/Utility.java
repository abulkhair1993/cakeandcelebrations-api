package com.cakeandcelebration.utils;

import java.util.Random;

public class Utility {
	
	public Utility() {
		
	}
	
	public static int generateRandomNumber() {
		Random random = new Random();
		return random.nextInt(999999);
	}

}
