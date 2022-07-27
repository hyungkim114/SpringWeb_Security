package com.spring.boardweb.service.user;

import com.spring.boardweb.entity.User;

public interface UserService {
	void join(User user);
	
	User idCheck(String userId);
}
