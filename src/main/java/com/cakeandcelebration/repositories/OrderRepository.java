package com.cakeandcelebration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cakeandcelebration.model.OrderDetails;


public interface OrderRepository extends JpaRepository<OrderDetails, Long>{
	
	  
	
	@Query(value = "SELECT c.name,o.summary FROM cake.customer c, cake.order_details o where c.uniqueid = o.uniqueid AND updated between CURRENT_DATE and (CURRENT_DATE+1)",nativeQuery = true)
	List<TodayOrder> findByUpdated();
	
	public static interface TodayOrder {

	     String getName();

	     String getSummary();

	  }
	
	

}
