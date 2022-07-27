package com.spring.boardweb.commons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.boardweb.entity.Board;
import com.spring.boardweb.entity.BoardFile;

//실제 서버에 파일 업로드 처리를 해주는 클래스
public class FileUtils {
	public List<BoardFile> parseFileInfo(int boardSeq, HttpServletRequest request,
			MultipartHttpServletRequest multipartServletRequest) throws IOException {
		List<BoardFile> fileList = new ArrayList<BoardFile>();
		
		//서버의 루트 경로 가져오기
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		
		String attachPath = "/upload/";
		
		File directory = new File(rootPath + attachPath);
		
		if(directory.exists() == false) {
			//서버 루트 경로에 upload 폴더 만들기
			directory.mkdir();
		}
		
		//첨부파일 목록 꺼내오기
		Iterator<String> iterator = multipartServletRequest.getFileNames();
		
		while(iterator.hasNext()) {
			//iterator에 담겨있는 파일이름들로 첨부파일 꺼내오기
			List<MultipartFile> list = multipartServletRequest.getFiles(iterator.next());
			
			for(MultipartFile multipartFile : list) {
				if(!multipartFile.isEmpty()) {
					BoardFile boardFile = new BoardFile();
					Board board = new Board();
					
					board.setBoardSeq(boardSeq);
					
					boardFile.setBoard(board);
					//화면에 표출할 때 사용
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					
					//고유한 파일명 생성
					//실제 서버에 저장되는 파일명
					String uuid = UUID.randomUUID().toString();
					boardFile.setFileName(uuid + multipartFile.getOriginalFilename());
					
					boardFile.setFilePath(rootPath + attachPath);
					
					fileList.add(boardFile);
					
					//파일 업로드 처리
					File file = new File(rootPath + attachPath + uuid + multipartFile.getOriginalFilename());
					
					multipartFile.transferTo(file);
				}
			}
		}
		
		return fileList;
	}
}
