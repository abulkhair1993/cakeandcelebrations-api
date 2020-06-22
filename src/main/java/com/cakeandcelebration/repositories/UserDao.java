package com.cakeandcelebration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cakeandcelebration.model.UserInfo;

public interface UserDao extends JpaRepository<UserInfo, Long> {
	
	UserInfo findUserByUserName(String userName); 

}
