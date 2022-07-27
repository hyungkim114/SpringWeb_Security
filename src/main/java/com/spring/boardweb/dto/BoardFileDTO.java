package com.spring.boardweb.dto;

import lombok.Data;

@Data
public class BoardFileDTO {
	private int boardSeq;
	private int fileSeq;
	private String originalFileName;
	private String fileName;
	private String filePath;
}
