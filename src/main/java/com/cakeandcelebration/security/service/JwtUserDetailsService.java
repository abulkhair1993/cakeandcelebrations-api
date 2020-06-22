package com.cakeandcelebration.security.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cakeandcelebration.exception.BadRequestException;
import com.cakeandcelebration.model.UserInfo;
import com.cakeandcelebration.model.UserInfoDTO;
import com.cakeandcelebration.repositories.UserDao;
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		UserInfo user = userDao.findUserByUserName(username);
		if(user == null) {
			throw new BadRequestException();
			
		}		
			return new User(user.getUserName(), user.getPassword(),
					new ArrayList<>());
		
	}

	public UserInfo save(UserInfoDTO user) {
		UserInfo newUser = new UserInfo();
		newUser.setUserName(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userDao.save(newUser);
	}

}