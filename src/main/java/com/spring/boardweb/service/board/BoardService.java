package com.spring.boardweb.service.board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.boardweb.entity.Board;
import com.spring.boardweb.entity.BoardFile;

public interface BoardService {
	Page<Board> getBoardList(Board board, Pageable pageable);
	
	int insertBoard(Board board);
	
	Board getBoard(int boardSeq);
	
	void deleteBoard(int boardSeq);
	
	void updateBoard(Board board);
	
	void insertBoardFileList(List<BoardFile> fileList);
	
	List<BoardFile> getBoardFileList(int boardSeq);
	
	void deleteBoardFile(int boardSeq, int fileSeq);
	
	//void addBoardFileList(List<BoardFile> fileList, int fileSeq);
}
