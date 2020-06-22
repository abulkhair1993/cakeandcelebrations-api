package com.cakeandcelebration.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize
public class OrderRequest {
	
	private String customerNo;

	private List<ProductDetails> productDetails;
	
	private String location;

}
