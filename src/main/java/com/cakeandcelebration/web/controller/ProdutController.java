package com.cakeandcelebration.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cakeandcelebration.model.Product;
import com.cakeandcelebration.repositories.ProductRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutController {

	@Autowired
	ProductRepository repo;

	@GetMapping("/api/v1/products")
	public ResponseEntity<List<Product>> getAllProucts() {
		
		List<Product> productList = new ArrayList<>();		
		try {
			productList = repo.findAll();
			return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);		
			
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("An error occured");
		}
	}

}
