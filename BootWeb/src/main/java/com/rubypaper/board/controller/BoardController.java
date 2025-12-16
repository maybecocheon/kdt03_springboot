package com.rubypaper.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rubypaper.board.domain.Board;
import com.rubypaper.board.security.SecurityUser;
import com.rubypaper.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	private final BoardService boardService;
	
	@GetMapping("/getBoardList")
	public void getBoardList(Board board, Model model) {
		Page<Board> boardList = boardService.getBoardList(board);
		model.addAttribute("boardList", boardList);
	}
	
	@GetMapping("/getBoard")
	public void getBoard(Board board, Model model) {
		model.addAttribute("board", boardService.getBoard(board));
	}
	
	@PostMapping("/updateBoard")
	public String updateBoard(Board board) {
		boardService.updateBoard(board);
		return "redirect:getBoardList";
	}
	
	@GetMapping("/insertBoard")
	public void insertBoardView() {}
	
	@PostMapping("/insertBoard")
	public String insertBoard(Board board, @AuthenticationPrincipal SecurityUser principal) {
		board.setMember(principal.getMember());
		boardService.insertBoard(board);
		return "redirect:getBoardList";
	}
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(Board board) {
		boardService.deleteBoard(board);
		return "redirect:getBoardList";
	}
}
