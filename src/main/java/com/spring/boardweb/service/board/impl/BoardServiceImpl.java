package com.spring.boardweb.service.board.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.boardweb.entity.Board;
import com.spring.boardweb.entity.BoardFile;
import com.spring.boardweb.mapper.BoardMapper;
import com.spring.boardweb.repository.BoardFileRepository;
import com.spring.boardweb.repository.BoardRepository;
import com.spring.boardweb.service.board.BoardService;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	BoardFileRepository boardFileRepository;
	
	@Autowired
	BoardMapper boardMapper;
	
	@Override
	public Page<Board> getBoardList(Board board, Pageable pageable) {
		if(board.getSearchKeyword() != null && !board.getSearchKeyword().equals("")) {
			if(board.getSearchCondition().equals("all")) {
				return boardRepository.findByBoardTitleContainingOrBoardWriterContainingOrBoardContentContaining(
							board.getSearchKeyword(),
							board.getSearchKeyword(),
							board.getSearchKeyword(),
							pageable
						);
			} else if(board.getSearchCondition().equals("title")) {
				return boardRepository.findByBoardTitleContaining(
							board.getSearchKeyword(), 
							pageable
						);
			} else if(board.getSearchCondition().equals("content")) {
				return boardRepository.findByBoardContentContaining(
						board.getSearchKeyword(), 
						pageable
					);
			} else if(board.getSearchCondition().equals("writer")) {
				return boardRepository.findByBoardWriterContaining(
						board.getSearchKeyword(), 
						pageable
					);
			} else {
				return null;
			}
		} else {
			return boardRepository.findAll(pageable);
		}
	}
	
	@Override
	public int insertBoard(Board board) {
		int boardSeq = boardMapper.getNextBoardSeq();
		board.setBoardSeq(boardSeq);
		boardRepository.save(board);
		boardRepository.flush();
		return board.getBoardSeq();
	}
	
	@Override
	public Board getBoard(int boardSeq) {
		return boardRepository.findById(boardSeq).get();
	}
	
	@Override
	public void deleteBoard(int boardSeq) {
		boardRepository.deleteById(boardSeq);
		
		boardMapper.updateBoardSeq(boardSeq);
	}
	
	@Override
	public void updateBoard(Board board) {
		boardRepository.save(board);
	}
	
	@Override
	public void insertBoardFileList(List<BoardFile> fileList) {
		//boardFileRepository.saveAll(fileList);
		//int i = 1;
		for(BoardFile boardFile : fileList) {
			boardFile.setFileSeq(boardFileRepository.selectNextFileSeqByBoardBoardSeq(boardFile.getBoard().getBoardSeq()));
			boardFileRepository.save(boardFile);
		}
	}
	
	@Override
	public List<BoardFile> getBoardFileList(int boardSeq) {
		
		Board board = new Board();
		
		board.setBoardSeq(boardSeq);
		
		List<BoardFile> fileList = boardFileRepository.findByBoard(board);
		
		if(fileList == null || fileList.isEmpty()) {
			return null;
		} else {
			return fileList;
		}
	}
	
	@Override
	public void deleteBoardFile(int boardSeq, int fileSeq) {
		Board board = new Board();
		board.setBoardSeq(boardSeq);
		
		BoardFile boardFile = new BoardFile();
		boardFile.setBoard(board);
		boardFile.setFileSeq(fileSeq);
		
		boardFileRepository.delete(boardFile);
	}
	
//	@Override
//	public void addBoardFileList(List<BoardFile> fileList, int fileSeq) {
//		for(BoardFile boardFile : fileList) {
//			boardFile.setFileSeq(fileSeq++);
//			boardFileRepository.save(boardFile);
//		}
//	}
}
