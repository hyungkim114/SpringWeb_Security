package com.spring.boardweb.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class BoardFileId implements Serializable{
	private int board;
	private int fileSeq;
}
