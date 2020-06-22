package com.cakeandcelebration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cakeandcelebration.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	public Customer findByMobile(String mobile);
	public Customer findByUniqueid(Long uniqueid);

}
