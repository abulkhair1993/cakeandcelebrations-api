package com.cakeandcelebration.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cakeandcelebration.exception.BadRequestException;
import com.cakeandcelebration.exception.UnRegisteredCustomerException;
import com.cakeandcelebration.model.Customer;
import com.cakeandcelebration.model.JwtRequest;
import com.cakeandcelebration.model.JwtResponse;
import com.cakeandcelebration.model.UserInfo;
import com.cakeandcelebration.model.UserInfoDTO;
import com.cakeandcelebration.repositories.CustomerRepository;
import com.cakeandcelebration.security.config.JwtTokenUtil;
import com.cakeandcelebration.security.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private CustomerRepository customerRepo;

	@RequestMapping(value = "/api/v1/authenticate/customer", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		if (StringUtils.isEmpty(authenticationRequest.getUsername().trim())
				|| StringUtils.isEmpty(authenticationRequest.getPassword().trim())) {
			throw new BadRequestException();
		}

		System.out.println(authenticationRequest.getUsername());
		System.out.println(authenticationRequest.getPassword());

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final Customer customer = customerRepo.findByMobile(authenticationRequest.getUsername());
		System.out.println(customer.getName());

		if (null == customer) {
			throw new BadRequestException();
		}

		final String uniqueId = String.valueOf(customer.getUniqueid());		
		if(authenticationRequest.getIsReg() == Boolean.TRUE) {
			return getToken(uniqueId, authenticationRequest.getUsername());
						
		} else {
			if (customer.getIsactive() && customer.getIsRegistered()) {
				return getToken(uniqueId, authenticationRequest.getUsername());
			} else {
				throw new UnRegisteredCustomerException();
			}			
		}
		
		
		
		/*
		 * if (authenticationRequest.getIsReg()) { customer.setIsactive(Boolean.TRUE);
		 * customer.setIsRegistered(Boolean.TRUE); customerRepo.save(customer); return
		 * getToken(uniqueId, authenticationRequest.getUsername()); } else { // existing
		 * user flow // check if customer is registered and active.Then return result
		 * else return // exception
		 * 
		 * }
		 */		

	}

	private ResponseEntity<?> getToken(String uniqueId, final String username) {
		final String token = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(username));
		return ResponseEntity.ok(new JwtResponse(token, uniqueId));

	}

	@RequestMapping(value = "/api/v1/register/customer", method = RequestMethod.POST)
	public ResponseEntity<String> saveUser(@RequestBody UserInfoDTO user) throws Exception {

		if (StringUtils.isEmpty(user.getUsername().trim()) || StringUtils.isEmpty(user.getPassword().trim())) {
			throw new BadRequestException();
		}

		UserInfo userInfo = userDetailsService.save(user);
		if (null == userInfo) {
			return new ResponseEntity<String>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>("created", HttpStatus.CREATED);
	}

	public void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new BadRequestException();
		} catch (BadCredentialsException e) {
			System.out.println("Mess" + e.getMessage());
			throw new BadRequestException();
		}
	}

}
