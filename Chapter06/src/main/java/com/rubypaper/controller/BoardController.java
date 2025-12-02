package com.rubypaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.rubypaper.domain.Board;
import com.rubypaper.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 목록 조회
	@GetMapping("/getBoardList")
	public String getBoardList(Model model) { // 프레임워크에서 인스턴스 생성해서 파라미터로 넘겨줌
		List<Board> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);	// request 영역에 속성으로 저장
		
		return "getBoardList";	// 뷰 이름
	}
	
	// 새 글 등록 시 뷰
	@GetMapping("/insertBoard")
	public String insertBoardView() {
		return "insertBoard";
	}
	
	// 새 글 등록 후 뷰
	@PostMapping("/insertBoard")
	public String insertBoard(Board board) {
		boardService.insertBoard(board);
		return "redirect:getBoardList";		// forward는 post끼리, get끼리만 가능
	}
	
	// 게시글 조회
	@GetMapping("/getBoard")
	public String getBoard(Board board, Model model) {
		model.addAttribute("board", boardService.getBoard(board));
		return "getBoard";
	}
	
	// 게시글 수정 뷰
	@GetMapping("/updateBoard")
	public String updateBoardView(Board board, Model model) {
		model.addAttribute("board", boardService.getBoard(board));
		return "updateBoard";
	}
	
	// 게시글 수정
	@PostMapping("/updateBoard")
	public String updateBoard(Board board) {
		boardService.updateBoard(board);
		return "redirect:getBoardList";
	}
	
	// 게시글 삭제
	@GetMapping("/deleteBoard")
	public String deleteBoard(Board board) {
		boardService.deleteBoard(board);
		return "forward:getBoardList";
	}
}
