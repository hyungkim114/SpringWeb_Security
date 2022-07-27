package com.spring.boardweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

//이 객체가 엔티티 객체임을 선언, 필수 값
@Entity
//테이블 어노테이션은 필수 값은 아님, 객체명과 테이블 명이 다를 때 테이블 명을 지정하기 위해 사용
@Table(name="T_USER")
@Data
@DynamicInsert
public class User {
	//키 값으로 지정
	//객체안에 있는 모든 속성 값들이 컬럼으로 생성됨
	@Id
	private String userId;
	
	//컬럼 어노테이션은 필수 값은 아니고
	//컬럼에 옵션을 주기 위해 사용
	//nullable: NOT NULL 설정
	//name: 컬럼명 설정
	//length: 컬럼에 저장되는 데이터의 최대 길이 설정
	//unique: 해당 컬럼을 유니크로 설정
	@Column(nullable=false)
	private String userPw;
	
	@Column(nullable=false)
	private String userNm;
	
	@Column(nullable=false)
	private String userMail;
	
	private String userTel;

	@Column
	@ColumnDefault("'ROLE_USER'")
	private String role;

	//@Transient
	//해당 속성값은 컬럼으로 생성하지 않음
	@Transient
	private String userAddress;
}
