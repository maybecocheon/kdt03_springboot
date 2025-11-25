package com.rubypaper.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.domain.BoardVO;

@RestController // @Controller 와 @ResponseBody 를 합친 것
public class BoardController {
	public BoardController() {
		System.out.println("===> BoardController 생성"); // 서버 구동 시 스프링 컨테이너에 컨트롤러 인스턴스 생성
	}

	@GetMapping("/hello") // 서블릿의 doGet 메서드 느낌
	public String hello(String name) {
		return "Hello, " + name + "!"; // getParameter()처럼 사용됨
	}
	
	@GetMapping("/getBoard")
	public BoardVO getBoard(Integer seq) {
		BoardVO board = new BoardVO();
		board.setSeq(seq);
		board.setWriter("테스터");
		return board;
	}

	@GetMapping("/getBoardList")
	public List<BoardVO> getBoardList() {
		List<BoardVO> boardList = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			BoardVO board = new BoardVO();
			board.setSeq(i);
			board.setTitle("제목 " + i);
			board.setWriter("테스터");
			board.setContent(i + "번 내용입니다");
			board.setCreateDate(new Date());
			board.setCnt(0);
			boardList.add(board);
		}
		return boardList;
	}
}
