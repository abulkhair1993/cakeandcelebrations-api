package com.cakeandcelebration.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "customer",schema = "cake")
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(GenerateUniqueId.class)
public class Customer implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ")
    @SequenceGenerator(sequenceName = "cake.customer_serial", allocationSize = 1, name = "CUSTOMER_SEQ")
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "email")
	private String email;
	
	@Id
	@Column(name = "uniqueid", unique=true, nullable = false)
	@SequenceGenerator(sequenceName = "cake.customer_unique_ser", allocationSize = 1, name = "CUST_UNIQUE_SEQ")	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_UNIQUE_SEQ")
	private Long uniqueid;
	
	@Column(name = "created")
	@CreationTimestamp
	private LocalDateTime created;
	
	@Column(name="isactive")
	private Boolean isactive;
	
	@Column(name="isregistered")
	private Boolean isRegistered;
	
}
