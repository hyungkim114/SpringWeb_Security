package com.spring.boardweb.dto;

import lombok.Data;

@Data
public class UserDTO {
	private String userId;
	private String userPw;
	private String userNm;
	private String userMail;
	private String userTel;
}
