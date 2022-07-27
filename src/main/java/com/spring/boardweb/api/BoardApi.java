package com.spring.boardweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boardweb.entity.Board;
import com.spring.boardweb.service.board.BoardService;

@RestController
public class BoardApi {
	@Autowired
	BoardService boardService;
	
	@GetMapping("/api/pageboardlist")
	public Page<Board> pageBoardList(@PageableDefault(page = 0, size = 10) Pageable pageable,
			Board board) {
		return boardService.getBoardList(board, pageable);
	}
}
