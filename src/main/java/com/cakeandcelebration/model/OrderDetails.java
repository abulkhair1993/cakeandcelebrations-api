package com.cakeandcelebration.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "order_details",schema= "cake")

public class OrderDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SEQ")
    @SequenceGenerator(sequenceName = "serial", allocationSize = 1, name = "ORDER_SEQ")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "uniqueid")
	private Long uniqueid;
	
	@Column(name = "summary")
	private String summary;
	
	@Column(name = "updated")
	@CreationTimestamp
	private LocalDateTime updated;
	
	@Column(name = "order_status")
	private String orderStatus;
	
	@Column(name = "location")
	private String location;
	
}
