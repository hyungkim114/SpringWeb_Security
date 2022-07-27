package com.spring.boardweb.controller.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boardweb.commons.FileUtils;
import com.spring.boardweb.entity.Board;
import com.spring.boardweb.entity.BoardFile;
import com.spring.boardweb.service.board.BoardService;

@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardService boardService;
	
	@GetMapping("/getBoardList")
	public ModelAndView getBoardListView(@PageableDefault(page = 0, size = 10) Pageable pageable,
			Board board) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/getBoardList.html");
		
		if(board.getSearchCondition() != null && !board.getSearchCondition().equals("")) {
			mv.addObject("searchCondition", board.getSearchCondition());
		}
		
		if(board.getSearchKeyword() != null && !board.getSearchKeyword().equals("")) {
			mv.addObject("searchKeyword", board.getSearchKeyword());
		}
		
		Page<Board> boardList = boardService.getBoardList(board, pageable);
		mv.addObject("boardList", boardList);
		
		return mv;
	}
	
	@GetMapping("/getBoard/{boardSeq}")
	public ModelAndView getBoardView(@PathVariable int boardSeq) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/getBoard.html");
		
		Board board = boardService.getBoard(boardSeq);
		List<BoardFile> fileList = boardService.getBoardFileList(boardSeq);
		
		mv.addObject("board", board);
		mv.addObject("fileList", fileList);
		
		return mv;
	}
	
	@GetMapping("/insertBoard")
	public ModelAndView insertBoardView() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/insertBoard.html");
		
		return mv;
	}
	
	@PostMapping("/insertBoard")
	public void insertBoard(HttpServletResponse response, Board board,
			HttpServletRequest request, MultipartHttpServletRequest multipartServletRequest) throws IOException {
		int boardSeq = boardService.insertBoard(board);
		
		System.out.println("boardSeq//////////////////////////" + boardSeq);
		
		FileUtils fileUtils = new FileUtils();
		List<BoardFile> fileList = fileUtils.parseFileInfo(boardSeq, request, multipartServletRequest);
		
		boardService.insertBoardFileList(fileList);
		
		//커맨드 객체란: 메소드에서 매개변수로 선언된 객체
		//           Getter, Setter가 필수적으로 존재해야됨
		//           View에서 보내준 데이터의 키값과 이름이 같은 속성값의 Setter가 자동 호출되어 세팅된다.
		//           커맨드 객체의 속성값중 int는 0으로 String은 ""으로 초기화됨
		Board board2 = new Board();
		System.out.println(board2.getBoardSeq());
		System.out.println(board.getBoardTitle());
		
		//RestController에서는 String으로 리다이렉트 불가능하여
		//response객체를 사용하여 리다이렉트한다.
		response.sendRedirect("/board/getBoardList");
	}
	
	@GetMapping("deleteBoard/{boardSeq}")
	public void deleteBoard(@PathVariable int boardSeq, HttpServletResponse response) throws IOException {
		boardService.deleteBoard(boardSeq);
		
		response.sendRedirect("/board/getBoardList");
	}
	
	@PostMapping("/updateBoard")
	public void updateBoard(Board board, HttpServletResponse response,
			HttpServletRequest request, MultipartHttpServletRequest multipartServletRequest) throws IOException {
		boardService.updateBoard(board);
		
		FileUtils fileUtils = new FileUtils();
		
		List<BoardFile> fileList = fileUtils.parseFileInfo(board.getBoardSeq(), request, multipartServletRequest);
		
		//boardService.addBoardFileList(fileList, fileSeq);
		
		boardService.insertBoardFileList(fileList);
		
		response.sendRedirect("/board/getBoardList");
	}
	
	@RequestMapping("/fileDown")
	public ResponseEntity<Resource> fileDown(@RequestParam String fileName, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/") + "/upload/";
		
		Resource resource = new FileSystemResource(path + fileName);
		
		String resourceName = resource.getFilename();
		
		HttpHeaders header = new HttpHeaders();
		
		try {
			header.add("Content-Disposition", "attachment; filename=" + new String(resourceName.getBytes("UTF-8"),
					"ISO-8859-1"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	@PostMapping("/deleteBoardFile")
	public void deleteBoardFile(@RequestParam int boardSeq, @RequestParam int fileSeq) {
		boardService.deleteBoardFile(boardSeq, fileSeq);
	}
}
