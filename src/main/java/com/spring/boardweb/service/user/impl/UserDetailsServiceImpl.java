package com.spring.boardweb.service.user.impl;

import com.spring.boardweb.entity.CustomUserDetails;
import com.spring.boardweb.entity.User;
import com.spring.boardweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//SecurityConfig.java에 설정한 .loginProcessionUrl("/user/loginProc");
// /user/loginProc 요청이 오면 자동으로 UserDetailsService의 loadUserByUserName 메소드가 실행

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
            User user = userRepository.findByUserId(userId);

            if(user == null) {
                return null;
            } else {
                // 시큐리티 세션에 정보가 등록됨
                return new CustomUserDetails(user);
            }
    }
}
