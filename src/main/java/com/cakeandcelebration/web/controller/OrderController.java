package com.cakeandcelebration.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cakeandcelebration.exception.BadRequestException;
import com.cakeandcelebration.model.OrderDetails;
import com.cakeandcelebration.model.OrderRequest;
import com.cakeandcelebration.repositories.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class OrderController {
	
	public final static String orderStatus ="Active";
	
	@Autowired
	private OrderRepository orderRepo;
		
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/api/v1/orders")	
	public void placeOrder(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {
		
		String uniqueId = null;
		
		if(orderRequest.getCustomerNo().isEmpty()) {
			throw new BadRequestException();
		} else {
			uniqueId = orderRequest.getCustomerNo();
		}
		
		// convert json object to string
		ObjectMapper objectMapper = new ObjectMapper();		
		String summary = objectMapper.writeValueAsString(orderRequest.getProductDetails());
		System.out.println("summary :: "+ summary);
		
		//Object creation
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setUniqueid(Long.valueOf(uniqueId.trim()));
		orderDetails.setSummary(summary);
		orderDetails.setOrderStatus(orderStatus);
		orderDetails.setLocation(orderRequest.getLocation());
		orderRepo.save(orderDetails);
		
	}
	
	@GetMapping("/api/v1/orders")
	public ResponseEntity<List<com.cakeandcelebration.repositories.OrderRepository.TodayOrder>> getOrders() {		
		
		List<com.cakeandcelebration.repositories.OrderRepository.TodayOrder> todayOrderList = new ArrayList<>();
		
		todayOrderList = orderRepo.findByUpdated();	
		
		
		return new ResponseEntity<List<com.cakeandcelebration.repositories.OrderRepository.TodayOrder>>(todayOrderList,HttpStatus.OK);
		
		
	}
	
	
	
}
