package com.rubypaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Member;
import com.rubypaper.service.BoardService;

@SessionAttributes("member")
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@ModelAttribute("member")
	public Member setMember() {
		return new Member();
	}
	
	// 목록 조회
	@GetMapping("/getBoardList")
	public String getBoardList(@ModelAttribute("member") Member member, Model model) { // 프레임워크에서 인스턴스 생성해서 파라미터로 넘겨줌
		if (member.getId() == null)
			return "redirect:login";
		List<Board> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);	// request 영역에 속성으로 저장
		
		return "getBoardList";	// 뷰 이름
	}
	
	// 새 글 등록 시 뷰
	@GetMapping("/insertBoard")
	public String insertBoardView(@ModelAttribute("member") Member member) {
		if (member.getId() == null)
			return "redirect:login";
		return "insertBoard";
	}
	
	// 새 글 등록 후 뷰
	@PostMapping("/insertBoard")
	public String insertBoard(@ModelAttribute("member") Member member, Board board) {
		if (member.getId() == null)
			return "redirect:login";
		boardService.insertBoard(board);
		return "redirect:getBoardList";		// forward는 post끼리, get끼리만 가능
	}
	
	// 게시글 조회
	@GetMapping("/getBoard")
	public String getBoard(@ModelAttribute("member") Member member, Board board, Model model) {
		if (member.getId() == null)
			return "redirect:login";
		model.addAttribute("board", boardService.getBoard(board));
		return "getBoard";
	}
	
	// 게시글 수정 뷰
	@GetMapping("/updateBoard")
	public String updateBoardView(@ModelAttribute("member") Member member, Board board, Model model) {
		if (member.getId() == null)
			return "redirect:login";
		model.addAttribute("board", boardService.getBoard(board));
		return "updateBoard";
	}
	
	// 게시글 수정
	@PostMapping("/updateBoard")
	public String updateBoard(@ModelAttribute("member") Member member, Board board) {
		if (member.getId() == null)
			return "redirect:login";
		boardService.updateBoard(board);
		return "redirect:getBoardList";
	}
	
	// 게시글 삭제
	@GetMapping("/deleteBoard")
	public String deleteBoard(@ModelAttribute("member") Member member, Board board) {
		if (member.getId() == null)
			return "redirect:login";
		boardService.deleteBoard(board);
		return "forward:getBoardList";
	}
	
	@GetMapping("/hello")
	public void hello(Model model) {
		model.addAttribute("greeting", "Hello, 타임리프!");
	}
	/*
	 1. 리턴 타입 void면 url명.html 호출
	 2. 리턴 타입 string이면 return명.html 호출
	 3. ModelAndView 객체 생성해서 Model, View Name을 설정해서 리턴
	 */
}
