package com.cakeandcelebration.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "userinfo",schema = "cake")
public class UserInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERINFO_SEQ")
    @SequenceGenerator(sequenceName = "cake.userinfo_serial", allocationSize = 1, name = "USERINFO_SEQ")
	@Column(name = "id", unique = true, nullable = false)	
	private Long id;
	@Column(name="username")
	private String userName;
	@Column(name="password")
	@JsonIgnore
	private String password;

}
