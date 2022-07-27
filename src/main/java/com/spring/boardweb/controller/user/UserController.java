package com.spring.boardweb.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boardweb.entity.User;
import com.spring.boardweb.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/join")
	//@RestController에서 화면을 반환하려면 ModelAndView 객체를 사용하여
	//화면을 반환한다.
	public ModelAndView joinView() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/join.html");
		
		return mv;
	}
	
	@GetMapping("/login")
	public ModelAndView loginView() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/login.html");
		
		return mv;
	}
	
	@PostMapping("/join")
	public ModelAndView join(User user) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/login.html");

		String pw= user.getUserPw();

		user.setUserPw(passwordEncoder.encode(pw));

		userService.join(user);
		
		return mv;
	}
	
	@PostMapping("/idCheck")
	public String idCheck(User user) {
		User idCheck = userService.idCheck(user.getUserId());
		
		if(idCheck == null) {
			return "idOk";
		} else {
			return "idFail";
		}
	}
	
	@PostMapping("/login")
	public String login(User user, HttpSession session) {
		User loginUser = userService.idCheck(user.getUserId());
		
		if(loginUser == null) {
			return "idFail";
		} else {
			if(!loginUser.getUserPw().equals(user.getUserPw())) {
				return "pwFail";
			} else {
				session.setAttribute("loginUser", loginUser);
				
				return "loginSuccess";
			}
		}
	}
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/login.html");
		
		return mv;
	}
}
