package com.spring.boardweb.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	int getNextBoardSeq();
	
	void updateBoardSeq(int boardSeq);
}
