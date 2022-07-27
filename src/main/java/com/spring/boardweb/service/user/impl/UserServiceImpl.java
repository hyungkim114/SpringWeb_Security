package com.spring.boardweb.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boardweb.entity.User;
import com.spring.boardweb.repository.UserRepository;
import com.spring.boardweb.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void join(User user) {
		userRepository.save(user);
	}
	
	@Override
	public User idCheck(String userId) {
		if(userRepository.findById(userId).isPresent()) {
			return userRepository.findById(userId).get();
		} else {
			return null;
		}
	}
}
