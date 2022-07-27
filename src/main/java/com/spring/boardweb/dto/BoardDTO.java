package com.spring.boardweb.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class BoardDTO {
	private int boardSeq;
	private String boardTitle;
	private String boardWriter;
	private String boardContent;
	private Date boardRegdate;
	private Date boardMdfdate;
	private int boardCnt;
}
