package com.cakeandcelebration.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cakeandcelebration.exception.BadRequestException;
import com.cakeandcelebration.exception.RegisteredCustomerException;
import com.cakeandcelebration.model.Customer;
import com.cakeandcelebration.model.CustomerDTO;
import com.cakeandcelebration.model.SecretPass;
import com.cakeandcelebration.model.SignUp;
import com.cakeandcelebration.model.UserInfo;
import com.cakeandcelebration.model.UserInfoDTO;
import com.cakeandcelebration.repositories.CustomerRepository;
import com.cakeandcelebration.repositories.UserDao;
import com.cakeandcelebration.security.controller.JwtAuthenticationController;
import com.cakeandcelebration.security.service.JwtUserDetailsService;
import com.cakeandcelebration.utils.Utility;

@RestController
public class CustomerController {

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UserDao userDao;

	@Autowired
	JwtAuthenticationController jwtAuthController;

	@PostMapping("/api/v1/customers")
	public ResponseEntity<SecretPass> addCustomer(@RequestBody Customer customer) {

		// Check if user already exists
		UserInfo user = userDao.findUserByUserName(customer.getMobile());
		if (null != user) {
			throw new BadRequestException();
		}
		// save customer
		customer.setIsactive(Boolean.FALSE);
		customer.setIsRegistered(Boolean.FALSE);
		customerRepo.save(customer);
		// generate pass
		String pass = String.valueOf(Utility.generateRandomNumber());
		SecretPass secretPass = new SecretPass();
		secretPass.setPass(pass);

		UserInfoDTO newUser = new UserInfoDTO();
		newUser.setUsername(customer.getMobile());
		newUser.setPassword(pass);

		userDetailsService.save(newUser);
		return new ResponseEntity<SecretPass>(secretPass, HttpStatus.CREATED);
	}

	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/api/v1/signup/customers")
	public ResponseEntity<HttpStatus> signUp(@RequestBody SignUp signUp) throws Exception {
		if (signUp.getSecretKey().trim().isEmpty()) {
			throw new BadRequestException();
		}

		jwtAuthController.authenticate(signUp.getMobile().trim(), signUp.getSecretKey().trim());
		Customer customer = customerRepo.findByMobile(signUp.getMobile());
		if (null == customer) {
			throw new BadRequestException();
		} else if (customer.getIsRegistered()) {
			throw new RegisteredCustomerException();
		} else if (null != customer && customer.getIsRegistered().equals(Boolean.FALSE)) {
			customer.setIsactive(Boolean.TRUE);
			customer.setIsRegistered(Boolean.TRUE);
			customerRepo.save(customer);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} else {
			throw new BadRequestException();
		}

	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/api/v1/customers")
	public ResponseEntity<List<CustomerDTO>> getCustomers() {
		List<Customer> customerList = new ArrayList<>();
		List<CustomerDTO> dtoList = new ArrayList<>();
		customerList = customerRepo.findAll();
		for (Customer customer : customerList) {
			CustomerDTO dto = new CustomerDTO();
			dto.setEmail(customer.getEmail());
			dto.setMobile(customer.getMobile());
			dto.setName(customer.getName());
			dtoList.add(dto);
		}

		return new ResponseEntity<>(dtoList, HttpStatus.OK);

	}

}
